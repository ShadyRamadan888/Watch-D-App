package com.example.watch_d.pojo;

import com.example.watch_d.pojo.MovieResult;

import java.util.List;

public class MovieRoot {

    public int page;
    public List<MovieResult> results;
    public int total_pages;
    public int total_results;


    public void setPage(int page) {
        this.page = page;
    }

    public void setResults(List<MovieResult> results) {
        this.results = results;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getPage() {
        return page;
    }

    public List<MovieResult> getResults() {
        return results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }
}
