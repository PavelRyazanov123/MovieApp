package com.example.movieapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.models.Response;
import com.squareup.picasso.Picasso;


import java.util.List;

import static com.example.movieapp.adapters.HomeParentAdapter.BASE_IMAGE_URL;

public class ChildHorizontalAdapter extends RecyclerView.Adapter<ChildHorizontalAdapter.HorizontalItemViewHolder> {

    private List<Response.MovieResponse> horizontalMovieList;
    private HomeParentAdapter.OnItemClickListener onItemClickListener;


    public void setOnItemClickListener(HomeParentAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setHorizontalMovieList(List<Response.MovieResponse> horizontalMovieList) {
        this.horizontalMovieList = horizontalMovieList;
    }

    @NonNull
    @Override
    public ChildHorizontalAdapter.HorizontalItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new HorizontalItemViewHolder(
                LayoutInflater
                        .from(viewGroup.getContext())
                        .inflate(R.layout.horizontal_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChildHorizontalAdapter.HorizontalItemViewHolder horizontalItemViewHolder, int i) {
        Response.MovieResponse movie = horizontalMovieList.get(i);
        horizontalItemViewHolder.horizontal_item_title.setText(movie.getTitle());
        Picasso.get()
                .load(BASE_IMAGE_URL + movie.getBackdropPath())
                .into(horizontalItemViewHolder.horizontal_item_icon);
    }

    @Override
    public int getItemCount() {
        return horizontalMovieList.size();
    }

    public class HorizontalItemViewHolder extends RecyclerView.ViewHolder {

        ImageView horizontal_item_icon;
        TextView horizontal_item_ganre;
        TextView horizontal_item_title;

        public HorizontalItemViewHolder(@NonNull View itemView) {
            super(itemView);

            horizontal_item_ganre = itemView.findViewById(R.id.horizontal_item_ganre);
            horizontal_item_icon = itemView.findViewById(R.id.detailed_small_icon);
            horizontal_item_title = itemView.findViewById(R.id.horizontal_item_title);

            itemView.setOnClickListener(v -> {
                String imageURL = BASE_IMAGE_URL + horizontalMovieList.get(getAdapterPosition()).getBackdropPath();
                String title = horizontalMovieList.get(getAdapterPosition()).getTitle();
                String story = horizontalMovieList.get(getAdapterPosition()).getOverview();
                onItemClickListener.onItemClick(imageURL, title, story);
            });
        }
    }


}
