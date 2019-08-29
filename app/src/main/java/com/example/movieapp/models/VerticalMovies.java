package com.example.movieapp.models;

public class VerticalMovies extends Movies {

    public VerticalMovies(Response.MovieResponse movieResponse) {
        this.moviesResponse = movieResponse;
    }

    private int position;

    private Response.MovieResponse moviesResponse;

    public Response.MovieResponse getMoviesResponse() {
        return moviesResponse;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public int getType() {
        return Movies.VERTICAL_MOVIE_TYPE;
    }

    @Override
    public int getPosition() {
        return position;
    }

}
