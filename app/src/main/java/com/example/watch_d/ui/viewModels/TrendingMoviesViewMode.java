package com.example.watch_d.ui.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.watch_d.pojo.MovieResult;
import com.example.watch_d.pojo.MovieRoot;
import com.example.watch_d.utils.RetroBuild;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class TrendingMoviesViewMode extends ViewModel {

    private MutableLiveData<List<MovieResult>> trendingMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<MovieResult>> trendingWeekMutableLiveData = new MutableLiveData<>();
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    private final RetroBuild retroBuild = new RetroBuild();
    public MutableLiveData<List<MovieResult>> getTrendingLiveData()
    {
        return trendingMutableLiveData;
    }

    public MutableLiveData<List<MovieResult>> getTrendingWeekLiveData()
    {
        return trendingWeekMutableLiveData;
    }

    public void getTrendingMovies()
    {
        Single<MovieRoot> observable = retroBuild.MoviesRequest().getTrendingDayMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        compositeDisposable.add(observable.subscribe(o->trendingMutableLiveData.setValue(o.getResults())));
    }

    public void getTrendingWeekMovies()
    {
        Single<MovieRoot> observable =retroBuild.MoviesRequest().getTrendingWeekMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        compositeDisposable.add(observable.subscribe(o->trendingWeekMutableLiveData.setValue(o.getResults())));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
