package com.example.watch_d.ui.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.watch_d.pojo.TVResult;
import com.example.watch_d.pojo.TVRoot;
import com.example.watch_d.utils.RetroBuild;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;import io.reactivex.rxjava3.disposables.CompositeDisposable;


public class PopularTVViewModel extends ViewModel {

    private MutableLiveData<List<TVResult>> popularTVMutableLiveData = new MutableLiveData<>();
    private RetroBuild retroBuild=new RetroBuild();

    public MutableLiveData<List<TVResult>> getLiveData()
    {
        return popularTVMutableLiveData;
    }


    CompositeDisposable compositeDisposable = new CompositeDisposable();
    public void getPopularTV()
    {
        Single<TVRoot> observable = retroBuild.MoviesRequest().getTVPopular()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        compositeDisposable.add(observable.subscribe(o->popularTVMutableLiveData.setValue(o.getResults())));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
