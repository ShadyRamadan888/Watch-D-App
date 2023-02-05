package com.example.watch_d.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.watch_d.R;
import com.example.watch_d.database.FavDatabase;
import com.example.watch_d.database.FavouriteEntity;
import com.example.watch_d.ui.adapters.FavAdapter;
import com.example.watch_d.ui.fragments.MovieDetailsFragment;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Favourites extends AppCompatActivity {

    private static final String TAG = "Favourites";
    private FavDatabase database;
    public FavAdapter postAdapter;
    Toolbar toolbar;
    private RecyclerView fav_recycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);


        database = FavDatabase.getInstance(this);

        toolbar = findViewById(R.id.toolbar);
        settingToolbar();
        postAdapter = new FavAdapter(Favourites.this);
        fav_recycler = findViewById(R.id.fav_recycler_view);
        fav_recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        fav_recycler.hasFixedSize();
        fav_recycler.setAdapter(postAdapter);


        insertMovie(MovieDetailsFragment.getEntity(),database);
    }

    public  void insertMovie(FavouriteEntity favouriteEntity,FavDatabase database)
    {
        database.postDao().insertFav(favouriteEntity)
                .subscribeOn(Schedulers.computation())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: error"+e.getMessage());
                    }
                });

    }

    public void gettingFavourites(FavDatabase database)
    {
        database.postDao().getFav()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<FavouriteEntity>>() {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onSuccess(@NonNull List<FavouriteEntity> favouriteEntities) {

                        postAdapter.setList(favouriteEntities);
                        postAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: gettingError"+e.getMessage());
                    }
                });
    }

    public void settingToolbar()
    {
        (Favourites.this).setSupportActionBar(toolbar);
        (Favourites.this).getSupportActionBar().setTitle("Your Favourites");
        (Favourites.this).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        (Favourites.this).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_24);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Favourites.this.onBackPressed();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        gettingFavourites(database);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}