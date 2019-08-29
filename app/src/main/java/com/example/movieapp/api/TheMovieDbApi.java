package com.example.movieapp.api;

import com.example.movieapp.models.Response;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TheMovieDbApi {


    @GET("3/movie/popular?")
    Observable<Response> getAll(@Query("page") int page);
}
