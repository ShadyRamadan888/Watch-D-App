package com.example.watch_d.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface FavDao {

    @Insert
    Completable insertFav(FavouriteEntity favouriteEntity);
    @Query("select * from fav_table")
    Single <List<FavouriteEntity>> getFav();
    @Query("delete from fav_table where id = :id")
    Completable deleteItem(int id);

}
