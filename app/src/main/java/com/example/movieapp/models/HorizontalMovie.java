package com.example.movieapp.models;

import java.util.ArrayList;
import java.util.List;

public class HorizontalMovie extends Movies {
    private List<Response.MovieResponse> horizontalMovieList = new ArrayList<>();
    private int position;

    public HorizontalMovie(List<Response.MovieResponse> horizontalMovieList, int position) {
        this.horizontalMovieList = horizontalMovieList;
        this.position = position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public int getType() {
        return HORIZONTAL_MOVIE_TYPE;
    }

    @Override
    public int getPosition() {
        return position;
    }

    public List<Response.MovieResponse> getHorizontalMovieList() {
        return horizontalMovieList;
    }

    public void setHorizontalMovieList(List<Response.MovieResponse> horizontalMovieList) {
        this.horizontalMovieList = horizontalMovieList;
    }
}
