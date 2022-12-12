package com.example.watch_d.ui.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.watch_d.pojo.MovieResult;
import com.example.watch_d.pojo.MovieRoot;
import com.example.watch_d.utils.RetroBuild;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TopRatedMovieViewModel extends ViewModel {

    private final MutableLiveData<List<MovieResult>> topRatedMutableMoviesData = new MutableLiveData<>();
    private final RetroBuild retroBuild = new RetroBuild();
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    public MutableLiveData<List<MovieResult>> getTopRatedMutableMoviesData()
    {
        return topRatedMutableMoviesData;
    }

    public void getTopRatedMovies()
    {
        Single<MovieRoot> observable = retroBuild.MoviesRequest().getMoviesTopRated()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        compositeDisposable.add(observable.subscribe(o->topRatedMutableMoviesData.setValue(o.getResults())));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
