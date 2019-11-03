package com.zack.gojektestjava.repository;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.google.gson.Gson;
import com.zack.gojektestjava.MyApplication;
import com.zack.gojektestjava.database.RepoDao;
import com.zack.gojektestjava.database.RepoDatabase;
import com.zack.gojektestjava.database.RepoEntity;
import com.zack.gojektestjava.model.RepoModel;
import com.zack.gojektestjava.rest.GithubRestClient;
import com.zack.gojektestjava.util.Executor;
import com.zack.gojektestjava.util.Response;
import com.zack.gojektestjava.util.SharePref;
import com.zack.gojektestjava.util.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;

import retrofit2.Call;
import retrofit2.Callback;

public class TrendingRepository {

//    private static TrendingRepository instance;
    private GithubRestClient restClient;
    private RepoDao dao;

    private MediatorLiveData<Response<List<RepoModel>>> liveData;

//    public static synchronized TrendingRepository getInstance() {
//        if (instance == null) {
//            instance = new TrendingRepository();
//        }
//
//        return instance;
//    }

    @Inject
    public TrendingRepository(GithubRestClient restClient, @Named("Database") RepoDatabase database) {
        this.restClient = restClient;
        dao = database.repoDao();
//        dao = MyApplication.getInstance().component.getDatabase().repoDao();
    }

    public LiveData<Response<List<RepoModel>>> getAllTrending() {
        liveData = new MediatorLiveData<>();
        new CheckCacheData(dao, restClient, callback).execute();
        liveData.addSource(dao.getAll(), entities -> {
            List<RepoModel> models = new ArrayList<>();
            Gson gson = new Gson();
            for (RepoEntity entity : entities) {
                models.add(new RepoModel().fromDatabaseModel(entity, gson));
            }

            liveData.setValue(Response.success(models));
        });

        return liveData;
    }

    public void refreshData() {
        restClient.getTrendingRepo(callback);
    }

    private Callback<List<RepoModel>> callback = new Callback<List<RepoModel>>() {
        @Override
        public void onResponse(Call<List<RepoModel>> call, retrofit2.Response<List<RepoModel>> response) {
            if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                new SaveToDB(dao, response.body()).execute();
            } else {
                liveData.setValue(Response.<List<RepoModel>>error("Empty response"));
            }
        }

        @Override
        public void onFailure(Call<List<RepoModel>> call, Throwable t) {
            liveData.setValue(Response.<List<RepoModel>>error("Error message"));
        }
    };

    /**
     * Check cache data in background
     * if there is no cache data or cache data is more than 2 hours ago,
     * call API to fetch new data
     */
    private static class CheckCacheData extends AsyncTask<Void, Void, Boolean> {
        private RepoDao dao;
        private GithubRestClient restClient;
        private Callback<List<RepoModel>> callback;

        public CheckCacheData(RepoDao dao, GithubRestClient restClient,
                              Callback<List<RepoModel>> callback) {
            this.dao = dao;
            this.restClient = restClient;
            this.callback = callback;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            RepoEntity entity = dao.getOne();
            return entity == null;
        }

        @Override
        protected void onPostExecute(Boolean isEmpty) {
            long saveTime = SharePref.getInstance().getLastUpdatedDate();
            long hoursDiff = Utilities.compareTime(saveTime, TimeUnit.MINUTES);
            if (isEmpty || hoursDiff > 120) {
                restClient.getTrendingRepo(callback);
            }
        }
    }

    /**
     * Save data to database in background
     */
    private static class SaveToDB extends AsyncTask<Void, Void, Void> {
        private RepoDao dao;
        private List<RepoEntity> entities;

        public SaveToDB(RepoDao dao, List<RepoModel> models) {
            this.dao = dao;
            final Gson gson = new Gson();
            entities = new ArrayList<>();
            for (RepoModel model : models) {
                entities.add(model.toDatabaseModel(gson));
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.updateData(entities);
            return null;
        }
    }
}
