package com.example.watch_d.IRequest;

import com.example.watch_d.pojo.vids.MovieVideoRoot;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IGetVid {

    @GET("3/movie/{movie_id}/videos")
    public Single<MovieVideoRoot> getPopularMoviesVideos(@Path("movie_id") int movie_id, @Query("api_key") String api_key);

    @GET("3/tv/{tv_id}/videos")
    public Single<MovieVideoRoot> getPopularTVVideos(@Path("tv_id") int tv_id,@Query("api_key") String api_key);
}
