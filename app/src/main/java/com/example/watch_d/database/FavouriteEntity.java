package com.example.watch_d.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "fav_table")
public class FavouriteEntity {

    @PrimaryKey
    private int id;
    private String poster_path;
    private String title;
    private String release_date;
    private float vote_average;

    public FavouriteEntity(int id,String poster_path, String title,String release_date,float vote_average) {
        this.poster_path = poster_path;
        this.title = title;
        this.id=id;
        this.release_date = release_date;
        this.vote_average = vote_average;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
