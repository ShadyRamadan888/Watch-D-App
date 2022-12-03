package com.example.watch_d.pojo;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MovieResult {

    public boolean adult;
    public  String backdrop_path;
    public ArrayList<Integer> genre_ids;
    public int id;
    public String original_language;
    public String original_title;
    public  String overview;
    public double popularity;
    public String poster_path;
    public String release_date;
    public String title;
    public boolean video;
    public double vote_average;
    public int vote_count;

    @BindingAdapter("android:loadImage")
    public static void loadImage(ImageView imageView,String url)
    {
        Glide.with(imageView).load("https://image.tmdb.org/t/p/w500"+url).into(imageView);
    }
}
