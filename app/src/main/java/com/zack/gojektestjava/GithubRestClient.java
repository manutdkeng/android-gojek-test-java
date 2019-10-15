package com.zack.gojektestjava;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class GithubRestClient {
    private static final String BASE_URL = "https://github-trending-api.now.sh";
    private static GithubRestClient instance;
    private GithubApiService service;

    interface GithubApiService {
        @GET("repositories")
        Call<List<RepoModel>> getTrendingRepo();
    }

    private GithubRestClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(GithubApiService.class);
    }

    public static synchronized GithubRestClient getInstance() {
        if (instance == null) {
            instance = new GithubRestClient();
        }

        return instance;
    }

    public void getTrendingRepo(Callback<List<RepoModel>> callback) {
        service.getTrendingRepo().enqueue(callback);
    }
}
