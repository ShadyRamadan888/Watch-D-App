package com.example.watch_d.pojo.vids;

import com.example.watch_d.pojo.vids.MovieVideoResult;

import java.util.List;

public class MovieVideoRoot {

    public int id;
    public List<MovieVideoResult> results;

    public int getId() {
        return id;
    }

    public List<MovieVideoResult> getResults() {
        return results;
    }
}
