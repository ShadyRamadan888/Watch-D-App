package com.example.watch_d.ui.viewModels;

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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularMovieViewModel extends ViewModel {

    private final MutableLiveData<List<MovieResult>> popularMutableLiveData = new MutableLiveData<>();
    private final RetroBuild retroBuild = new RetroBuild();

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MutableLiveData<List<MovieResult>> getPopularLiveData()
    {
        return popularMutableLiveData;
    }

    public void getPopularMovies()
    {
        Single<MovieRoot> observable = retroBuild.MoviesRequest().getPopularMovie()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        compositeDisposable.add(observable.subscribe(o->popularMutableLiveData.setValue(o.getResults())));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
