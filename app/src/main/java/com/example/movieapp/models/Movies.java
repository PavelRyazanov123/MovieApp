package com.example.movieapp.models;

public abstract class Movies {

    public static final int VERTICAL_MOVIE_TYPE = 1;
    public static final int HORIZONTAL_MOVIE_TYPE = 0;
    public static final int HEADER_TYPE = 2;

    public abstract int getType();

    public abstract int getPosition();
}
