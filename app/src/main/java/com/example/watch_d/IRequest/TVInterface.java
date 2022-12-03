package com.example.watch_d.IRequest;

import com.example.watch_d.pojo.TVRoot;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TVInterface {

    @GET("3/tv/{popular}")
    public Single<TVRoot> getPopularTV(@Path("popular") String popular, @Query("page") int page, @Query("api_key") String api_key);

}
