package com.example.movieapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.models.Headers;
import com.example.movieapp.models.HorizontalMovie;
import com.example.movieapp.models.Movies;
import com.example.movieapp.models.Response;
import com.example.movieapp.models.VerticalMovies;
import com.squareup.picasso.Picasso;
import java.util.List;

public class HomeParentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500/";
    private List<Movies> moviesList;
    private OnItemClickListener listener;


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setMoviesList(List<Movies> moviesList) {
        this.moviesList = moviesList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case (Movies.HEADER_TYPE):
                return new HeaderViewHolder(
                        LayoutInflater
                                .from(viewGroup.getContext())
                                .inflate(R.layout.header_item, viewGroup, false));
            case (Movies.HORIZONTAL_MOVIE_TYPE):

                return new HorizontalMovieViewHolder(
                        LayoutInflater
                                .from(viewGroup.getContext())
                                .inflate(R.layout.horizontal_recyclerview_layout, viewGroup, false));
            case (Movies.VERTICAL_MOVIE_TYPE):
                return new VerticalMovieViewHolder(
                        LayoutInflater.from(viewGroup.getContext())
                                .inflate(R.layout.vertical_item, viewGroup, false));
            default:
                return null;
        }
    }

    private static final String TAG = "HomeParentAdapter";

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        Movies movies = moviesList.get(i);

        if (movies instanceof HorizontalMovie) {
            ChildHorizontalAdapter childHorizontalAdapter = new ChildHorizontalAdapter();
            List<Response.MovieResponse> horizontalMovieList = ((HorizontalMovie) movies).getHorizontalMovieList();
            childHorizontalAdapter.setHorizontalMovieList(horizontalMovieList);
            ((HorizontalMovieViewHolder) holder).recyclerView.setAdapter(childHorizontalAdapter);

            childHorizontalAdapter.setOnItemClickListener(listener);

        } else if (movies instanceof Headers) {
            ((HeaderViewHolder) holder).headerText.setText(((Headers) movies).getText());
        } else if (movies instanceof VerticalMovies) {
            Response.MovieResponse movieResponse = ((VerticalMovies) movies).getMoviesResponse();
            ((VerticalMovieViewHolder) holder).vertical_movie_title.setText(movieResponse.getTitle());
            Picasso.get()
                    .load(BASE_IMAGE_URL + movieResponse.getBackdropPath())
                    .into(((VerticalMovieViewHolder) holder).vertical_movie_icon);
        }

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return moviesList.get(position).getType();
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        TextView headerText;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            headerText = itemView.findViewById(R.id.header);
        }
    }

    public class HorizontalMovieViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;

        public HorizontalMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.horizontal_recyclerView);
        }
    }

    public class VerticalMovieViewHolder extends RecyclerView.ViewHolder {

        TextView vertical_movie_genre;
        TextView vertical_movie_title;
        ImageView vertical_movie_icon;

        public VerticalMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            vertical_movie_genre = itemView.findViewById(R.id.vertical_item_genre);
            vertical_movie_icon = itemView.findViewById(R.id.vertical_item_icon);
            vertical_movie_title = itemView.findViewById(R.id.vertical_item_title);

            itemView.setOnClickListener(v -> {
                Movies movie = moviesList.get(getAdapterPosition());
                String imageURL = BASE_IMAGE_URL + ((VerticalMovies) movie).getMoviesResponse().getBackdropPath();
                String title = ((VerticalMovies) movie).getMoviesResponse().getTitle();
                String story = ((VerticalMovies) movie).getMoviesResponse().getOverview();
                listener.onItemClick(imageURL, title, story);
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String imageURL, String title, String story);
    }
}

