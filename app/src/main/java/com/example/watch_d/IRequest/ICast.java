package com.example.watch_d.IRequest;

import com.example.watch_d.pojo.cast_and_crew.Cast;
import com.example.watch_d.pojo.cast_and_crew.CastRoot;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ICast {

    @GET("3/movie/{movie_id}/credits")
    public Single<CastRoot> getMovieCast(@Path("movie_id") int id, @Query("api_key") String api_key);

    @GET("3/tv/{tv_id}/credits")
    public Single<CastRoot> getTVCast(@Path("tv_id")int id,@Query("api_key") String api_key);
}
