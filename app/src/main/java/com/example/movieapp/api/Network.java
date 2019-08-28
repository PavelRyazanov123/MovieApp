package com.example.movieapp.api;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {
    private static Network mInstance;
    private String BASE_URL = "http://api.themoviedb.org/";
    private Retrofit retrofit;

    private Network() {
        retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }

    public static Network getInstance() {
        if (mInstance == null)
            mInstance = new Network();
        return mInstance;
    }

    public TheMovieDbApi getApi (){
        return retrofit.create(TheMovieDbApi.class);
    }
}
