package com.example.watch_d.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.watch_d.R;
import com.example.watch_d.pojo.vids.MovieVideoRoot;
import com.example.watch_d.ui.adapters.PopularMoviesAdapter;
import com.example.watch_d.ui.adapters.PopularTVAdapter;
import com.example.watch_d.ui.adapters.TopRatedMoviesAdapter;
import com.example.watch_d.ui.adapters.TopRatedTVsAdapter;
import com.example.watch_d.ui.adapters.TrendingMovieAdapter;
import com.example.watch_d.utils.RetroBuild;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowVidActivity extends YouTubeBaseActivity {


    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer.OnInitializedListener listener;
    private RetroBuild retroBuild = new RetroBuild();
    CompositeDisposable compositeDisposable = new CompositeDisposable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_vid);

        youTubePlayerView = findViewById(R.id.youtube_player_view);

        Intent intent = getIntent();
        int id = intent.getExtras().getInt("id");
        String type = intent.getExtras().getString("type");

        if(Objects.equals(type, "POPULAR_TV"))
             getTVVid(id);
        if(Objects.equals(type, "POPULAR_MOVIES"))
            getMovieVid(id);
        if(Objects.equals(type,"TREND_MOVIES"))
            getTrendingMoviesVid(id);
        if(Objects.equals(type,"TOP_RATED_MOVIE"))
            getTopRatedMovieVid(id);
        if(Objects.equals(type,"TOP_RATED_TV"))
            getTopRatedTVsVid(id);

    }

    private void getTrendingMoviesVid(int id)
    {
        Single<MovieVideoRoot> observable = retroBuild.getPopMoviesVideos(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        try{
            compositeDisposable.add(observable.subscribe(o->vidFun(o.getResults().get(TrendingMovieAdapter.pTrend).key)));
        }
        catch (Exception e)
        {
            Toast.makeText(ShowVidActivity.this, "Video Not Found", Toast.LENGTH_SHORT).show();
        }

        TrendingMovieAdapter.pTrend = 0;
    }

    public void getMovieVid(int id)
    {

        Single<MovieVideoRoot> observable =retroBuild.getPopMoviesVideos(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        try {
            compositeDisposable.add(observable
                    .doOnError(throwable -> Toast.makeText(this, "Error"+throwable.getMessage(), Toast.LENGTH_SHORT).show())
                    .subscribe(o->vidFun(o.getResults().get(PopularMoviesAdapter.p).key)));
        }
        catch (Exception e)
        {
            Toast.makeText(ShowVidActivity.this, "Video Not Found", Toast.LENGTH_SHORT).show();
        }

        PopularMoviesAdapter.p = 0;
    }
    public void getTVVid(int id)
    {
        Single<MovieVideoRoot> observable = retroBuild.getPopTVVideos(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        try{
            compositeDisposable.add(observable.subscribe(o->vidFun(o.getResults().get(PopularTVAdapter.pTV).key)));
        }catch (Exception e)
        {
            Toast.makeText(ShowVidActivity.this, "Video Not Found", Toast.LENGTH_SHORT).show();
        }
        PopularTVAdapter.pTV = 0;
    }

    public void getTopRatedMovieVid(int id)
    {
        Single<MovieVideoRoot> observable = retroBuild.MoviesRequest().getPopMoviesVideos(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        try {
            compositeDisposable.add(observable.subscribe(o->vidFun(o.getResults().get(TopRatedMoviesAdapter.pTopRatedMovies).key)));
        }
        catch (Exception e)
        {
            Toast.makeText(ShowVidActivity.this, "Video Not Found", Toast.LENGTH_SHORT).show();
        }
        TopRatedMoviesAdapter.pTopRatedMovies = 0;
    }
    public void getTopRatedTVsVid(int id)
    {
        Single<MovieVideoRoot> observable = retroBuild.MoviesRequest().getPopTVVideos(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        try {
            compositeDisposable.add(observable.subscribe(o->vidFun(o.getResults().get(TopRatedTVsAdapter.pTopRatedTV).key)));
        }
        catch (Exception e)
        {
            Toast.makeText(ShowVidActivity.this, "Video Not Found", Toast.LENGTH_SHORT).show();
        }
        TopRatedTVsAdapter.pTopRatedTV = 0;
    }
    public void vidFun(String key)
    {

        listener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

                String videoId = key;
                youTubePlayer.loadVideo(videoId);
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                Toast.makeText(ShowVidActivity.this, "Initialization failed", Toast.LENGTH_SHORT).show();
            }
        };
        youTubePlayerView.initialize("AIzaSyDTdq6ZN7-rJh64-NRvVtqFtibHqq8k7T8",listener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        compositeDisposable.clear();
    }
}