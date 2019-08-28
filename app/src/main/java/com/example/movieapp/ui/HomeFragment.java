package com.example.movieapp.ui;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.movieapp.R;
import com.example.movieapp.adapters.HomeParentAdapter;
import com.example.movieapp.models.Movies;
import com.example.movieapp.viewmodel.HomeViewModel;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeFragment extends Fragment implements HomeParentAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "HomeFragment";
    public static final String IMAGE_URL_KEY = "IMAGE_URL_KEY";
    public static final String STORY_KEY = "STORY_KEY";
    public static final String TITLE_KEY = "TITLE_KEY";
    private HomeParentAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Group errorGroup;
    private HomeViewModel homeViewModel;
    private List<Movies> moviesList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.home_recyclerView);
        swipeRefreshLayout = view.findViewById(R.id.home_swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        errorGroup = view.findViewById(R.id.group);
        MaterialButton tryAgainButton = view.findViewById(R.id.home_try_again);

        adapter = new HomeParentAdapter();
        adapter.setOnItemClickListener(this);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(Movies.HORIZONTAL_MOVIE_TYPE, 1);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(Movies.HEADER_TYPE, 2);
        recyclerView.setAdapter(adapter);

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        homeViewModel.getMovies().observe(this, moviesObserver);
        homeViewModel.getIsErrorVisible().observe(this, errorGroupObserver);
        homeViewModel.getIsRefreshing().observe(this, refreshingObserver);

        tryAgainButton.setOnClickListener(v -> homeViewModel.refreshData());

        if (moviesList != null) {
            adapter.setMoviesList(moviesList);
        }

        return view ;
    }

    private Observer<List<Movies>> moviesObserver = movies -> {
        moviesList = movies;
        adapter.setMoviesList(movies);
        adapter.notifyDataSetChanged();
    };

    private Observer<Boolean> refreshingObserver = isRefreshing -> swipeRefreshLayout.setRefreshing(isRefreshing);

    private Observer<Boolean> errorGroupObserver = isError -> {
        if (isError == true) {
            swipeRefreshLayout.setVisibility(View.GONE);
            errorGroup.setVisibility(View.VISIBLE);
        } else {
            swipeRefreshLayout.setVisibility(View.VISIBLE);
            errorGroup.setVisibility(View.GONE);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        homeViewModel = null;
        moviesList = null;
    }

    @Override
    public void onItemClick(String imageURL, String title, String story) {
        if (getActivity() != null) {
            Bundle bundle = new Bundle();
            bundle.putString(IMAGE_URL_KEY, imageURL);
            bundle.putString(TITLE_KEY, title);
            bundle.putString(STORY_KEY, story);
            ((OnFragmentChange) getActivity()).changeFragment(bundle);
        }
    }

    @Override
    public void onRefresh() {
        homeViewModel.refreshData();
    }

    public interface OnFragmentChange {
        void changeFragment(Bundle bundle);
    }

}
