package com.example.movieapp.viewmodel;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieapp.api.Network;
import com.example.movieapp.models.Headers;
import com.example.movieapp.models.HorizontalMovie;
import com.example.movieapp.models.Movies;
import com.example.movieapp.models.Response;
import com.example.movieapp.models.VerticalMovies;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class HomeViewModel extends ViewModel {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private static final String TAG = "HomeViewModel";
    private MutableLiveData<List<Movies>> movies = new MutableLiveData<>();
    private MutableLiveData<Boolean> isRefreshing = new MutableLiveData<>();
    private MutableLiveData<Boolean> isErrorVisible = new MutableLiveData<>();

    public HomeViewModel() {
        Log.e(TAG, "Recreated" );
        refreshData();
    }

    public LiveData<List<Movies>> getMovies() {
        return movies;
    }

    public LiveData<Boolean> getIsRefreshing() {
        return isRefreshing;
    }

    public LiveData<Boolean> getIsErrorVisible() {
        return isErrorVisible;
    }



    public void refreshData() {
        compositeDisposable.add(
                Network.getInstance()
                        .getApi()
                        .getAll(1)
                        .subscribeOn(Schedulers.io())
                        .timeout(2000, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable -> {
                            isRefreshing.setValue(true);
                        })
                        .map(Response::getResults)
                        .flatMap(Observable::fromIterable)
                        .toSortedList((x1, x2) -> Double.compare(x2.getVoteAverage(), x1.getVoteAverage()))
                        .subscribe(
                                result -> {
                                    isRefreshing.setValue(false);
                                    isErrorVisible.setValue(false);
                                    movies.setValue(toMoviesList(result));
                                    },
                                throwable -> {
                                    Log.e(TAG, "refreshData: ",throwable );
                                    isRefreshing.setValue(false);
                                    isErrorVisible.setValue(true);
                                }));
    }

    private List<Movies> toMoviesList(List<Response.MovieResponse> movieResponses) {
        List<Movies> moviesList = new ArrayList<>();

        moviesList.add(new Headers("Top 10", 0));
        moviesList.add(new HorizontalMovie(movieResponses.subList(0, 10), 1));
        moviesList.add(new Headers("Popular", 2));

        //Skip Top 10
        for (int i = 10; i < movieResponses.size(); i++) {
            moviesList.add(new VerticalMovies(movieResponses.get(i)));
        }

        return moviesList;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (!compositeDisposable.isDisposed())
        compositeDisposable.dispose();
    }

    private List<Movies> addMovies(List<Movies> list, Movies... movies) {
        for (Movies movie : movies) {
            if (movie.getPosition() > list.size())
                list.add(list.size(), movie);
            else
                list.add(movie.getPosition(), movie);
        }
        return list;
    }
}
