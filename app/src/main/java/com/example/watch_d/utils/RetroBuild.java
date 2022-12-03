package com.example.watch_d.utils;

import com.example.watch_d.IRequest.ICast;
import com.example.watch_d.IRequest.IGetVid;
import com.example.watch_d.IRequest.MoviesInterface;
import com.example.watch_d.IRequest.TVInterface;
import com.example.watch_d.pojo.MovieRoot;
import com.example.watch_d.pojo.cast_and_crew.Cast;
import com.example.watch_d.pojo.cast_and_crew.CastRoot;
import com.example.watch_d.pojo.vids.MovieVideoRoot;
import com.example.watch_d.pojo.TVRoot;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroBuild {

    private static final String key = "e0bd6b7b07bcd271f8b131a201f58222";
    private static final String BASE_URL="https://api.themoviedb.org/";
    private static MoviesInterface moviesInterface;
    private static TVInterface tvInterface;
    private static IGetVid iGetVid;
    private static ICast iCast;
    private RetroBuild INSTANCE;

    public RetroBuild()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        moviesInterface = retrofit.create(MoviesInterface.class);
        tvInterface = retrofit.create(TVInterface.class);
        iGetVid = retrofit.create(IGetVid.class);
        iCast = retrofit.create(ICast.class);

    }
    public RetroBuild MoviesRequest()
    {
        if(INSTANCE == null)
            INSTANCE = new RetroBuild();
        return INSTANCE;
    }
    public Single<MovieRoot> getPopularMovie()
    {
        return moviesInterface.getPopularMovies("popular",key);
    }


    public Single<TVRoot> getTVPopular()
    {
            return tvInterface.getPopularTV("popular",8,key);
    }
    public Single<MovieRoot> getSliderImages()
    {
        return moviesInterface.getImagesForHomeSlider("top_rated",40,key);
    }
    public Single<MovieRoot> getTrendingDayMovies()
    {
        return moviesInterface.getTrendingMovies("movie","day",7,key);

    }
    public Single<MovieRoot> getTrendingWeekMovies()
    {
        return moviesInterface.getTrendingMovies("movie","week",9,key);
    }
    public Single<MovieVideoRoot> getPopMoviesVideos(int id)
    {
        return iGetVid.getPopularMoviesVideos(id,key);
    }

    public Single<MovieVideoRoot> getPopTVVideos(int id)
    {
        return iGetVid.getPopularTVVideos(id,key);
    }

    public Single<MovieRoot> getMoviesTopRated()
    {
        return moviesInterface.getMoviesTopRated("top_rated",16,key);
    }


    public Single<CastRoot> getMovieCast(int id)
    {
        return iCast.getMovieCast(id,key);
    }
    public Single<CastRoot> getTVCast(int id)
    {
        return iCast.getTVCast(id,key);
    }

}
