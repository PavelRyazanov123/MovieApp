package com.example.movieapp.api;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {
    private static Network mInstance;
    private String BASE_URL = "http://api.themoviedb.org/";
    private String api_key = "befc7872520fd736c58948abb2f4a53c";
    private Retrofit retrofit;

    private Network() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    HttpUrl httpUrl = original.url();

                    HttpUrl newHttpUrl = httpUrl
                            .newBuilder()
                            .addQueryParameter("api_key", api_key)
                            .build();

                    Request.Builder requestBuilder = original
                            .newBuilder()
                            .url(newHttpUrl);

                    Request request = requestBuilder.build();

                    return chain.proceed(request);
                }).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Network getInstance() {
        if (mInstance == null)
            mInstance = new Network();
        return mInstance;
    }

    public TheMovieDbApi getApi() {
        return retrofit.create(TheMovieDbApi.class);
    }
}
