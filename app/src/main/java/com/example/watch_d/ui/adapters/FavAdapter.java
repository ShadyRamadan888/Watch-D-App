package com.example.watch_d.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.watch_d.R;
import com.example.watch_d.database.FavDatabase;
import com.example.watch_d.database.FavouriteEntity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import kotlin.jvm.JvmName;


public class FavAdapter extends RecyclerView.Adapter<FavAdapter.FavViewHolder> {

    private List<FavouriteEntity> favouriteEntityList = new ArrayList<>();
    private Context context;
    private FavDatabase database;


    public FavAdapter(Context context) {
        this.context = context;
        database = FavDatabase.getInstance(context);
    }

    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.fav_movie_title.setText(favouriteEntityList.get(position).getTitle());
        holder.fav_rate.setRating(favouriteEntityList.get(position).getVote_average());
        holder.fav_date.setText(favouriteEntityList.get(position).getRelease_date());
        Glide.with(context).load("https://image.tmdb.org/t/p/w500"+favouriteEntityList.get(position).getPoster_path()).into(holder.fav_img);
        //Deleting movie from favourite (Room)
        holder.delete_fav_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    database.postDao().deleteItem(favouriteEntityList.get(position).getId())
                            .subscribeOn(Schedulers.computation())
                            .subscribe(new CompletableObserver() {
                                @Override
                                public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                                }

                                @Override
                                public void onComplete() {

                                }

                                @Override
                                public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                                }
                            });
                favouriteEntityList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,favouriteEntityList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return favouriteEntityList.size();
    }

    public class FavViewHolder extends RecyclerView.ViewHolder {

        ImageView fav_img;
        TextView fav_movie_title;
        TextView fav_date;
        ImageView delete_fav_btn;
        RatingBar fav_rate;
        public FavViewHolder(@NonNull View itemView) {
            super(itemView);
            fav_img = itemView.findViewById(R.id.image_fav);
            fav_movie_title = itemView.findViewById(R.id.fav_movie_title);
            delete_fav_btn = itemView.findViewById(R.id.delete_fav_btn);
            fav_rate = itemView.findViewById(R.id.fav_rating);
            fav_date = itemView.findViewById(R.id.fav_movie_date);
        }
    }

    @JvmName(name = "setList1")
    public void setList(List<FavouriteEntity> list)
    {
        this.favouriteEntityList = list;
        notifyDataSetChanged();
    }

}
