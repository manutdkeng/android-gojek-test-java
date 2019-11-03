package com.zack.gojektestjava.rest;

import com.zack.gojektestjava.model.RepoModel;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

@Singleton
public class GithubRestClient {
    private static final String BASE_URL = "https://github-trending-api.now.sh";
    //    private static GithubRestClient instance;
    private GithubApiService service;

    interface GithubApiService {
        @GET("repositories")
        Call<List<RepoModel>> getTrendingRepo();
    }

    @Inject
    public GithubRestClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build();

        service = retrofit.create(GithubApiService.class);
    }

//    public static synchronized GithubRestClient getInstance() {
//        if (instance == null) {
//            instance = new GithubRestClient();
//        }
//
//        return instance;
//    }

    public void getTrendingRepo(Callback<List<RepoModel>> callback) {
        service.getTrendingRepo().enqueue(callback);
    }
}
