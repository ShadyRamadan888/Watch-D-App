package com.example.watch_d.pojo;

import com.example.watch_d.pojo.TVResult;

import java.util.List;

public class TVRoot {

    public int page;
    public List<TVResult> results;
    public int total_pages;
    public int total_results;

    public int getPage() {
        return page;
    }

    public List<TVResult> getResults() {
        return results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }
}
