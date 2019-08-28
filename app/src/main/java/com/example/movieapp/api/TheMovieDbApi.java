package com.example.movieapp.api;

import com.example.movieapp.models.Response;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TheMovieDbApi {

    //api_key=befc7872520fd736c58948abb2f4a53c

    @GET("3/movie/popular?api_key=befc7872520fd736c58948abb2f4a53c")
    Observable<Response> getAll (@Query("page") int page);
}
