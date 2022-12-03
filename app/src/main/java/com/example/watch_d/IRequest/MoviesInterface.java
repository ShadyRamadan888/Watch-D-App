package com.example.watch_d.IRequest;

import com.example.watch_d.pojo.MovieRoot;
import com.example.watch_d.pojo.TVRoot;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesInterface {

    @GET("3/movie/{popular}")
    public Single<MovieRoot> getPopularMovies(@Path("popular") String sort, @Query("api_key") String api_key);

    @GET("3/movie/{top_rated}")
    public Single<MovieRoot> getImagesForHomeSlider(@Path("top_rated") String sort,@Query("page") int page, @Query("api_key") String api_key);

    @GET("3/trending/{media_type}/{time_window}")
    public Single<MovieRoot> getTrendingMovies(@Path("media_type") String media_type, @Path("time_window") String time_window,@Query("page") int page, @Query("api_key") String api_key);

    @GET("3/movie/{top_rated}")
    public Single<MovieRoot> getMoviesTopRated(@Path("top_rated") String sort,@Query("page") int page, @Query("api_key") String api_key);



}
