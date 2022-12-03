package com.example.watch_d.ui.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.watch_d.pojo.cast_and_crew.Cast;
import com.example.watch_d.pojo.cast_and_crew.CastRoot;
import com.example.watch_d.utils.RetroBuild;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CastViewModel extends ViewModel {

    private MutableLiveData<List<Cast>> castMovieMutable = new MutableLiveData<>();
    private MutableLiveData<List<Cast>> castTVMutable = new MutableLiveData<>();

    private RetroBuild retroBuild = new RetroBuild();
    public MutableLiveData<List<Cast>> getMovieCastLive()
    {
        return castMovieMutable;
    }
    public MutableLiveData<List<Cast>> getTVCastLive()
    {
        return castTVMutable;
    }

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void getMovieCast(int id)
    {
       Single<CastRoot> observable = retroBuild.MoviesRequest().getMovieCast(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        compositeDisposable.add(observable.subscribe(o->castMovieMutable.setValue(o.getCast())));

    }

    public void getTVCast(int id)
    {
       Single<CastRoot> observable = retroBuild.MoviesRequest().getTVCast(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        compositeDisposable.add(observable.subscribe(o->castTVMutable.setValue(o.getCast())));
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        compositeDisposable.clear();
    }
}
