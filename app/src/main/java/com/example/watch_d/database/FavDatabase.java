package com.example.watch_d.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = {FavouriteEntity.class},version = 3)
public abstract class FavDatabase extends RoomDatabase {

    private static FavDatabase instance;
    public abstract FavDao postDao();


    public static synchronized FavDatabase getInstance(Context context)
    {
        if (instance==null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            FavDatabase.class,
                            "fav_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
