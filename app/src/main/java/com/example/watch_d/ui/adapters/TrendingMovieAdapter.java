package com.example.watch_d.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watch_d.databinding.BaseItemBinding;
import com.example.watch_d.pojo.MovieResult;
import com.example.watch_d.utils.MusclesListAdapteListener;

import java.util.List;

public class TrendingMovieAdapter extends RecyclerView.Adapter<TrendingMovieAdapter.TrendingViewHolder> {


    public static int pTrend;
    List<MovieResult> arrayList;
    public static MovieResult movie_position = new MovieResult();
    Context context;
    public MusclesListAdapteListener musclesListAdapteListener;
    public TrendingMovieAdapter(List<MovieResult> arrayList, Context context, MusclesListAdapteListener musc) {
        this.arrayList = arrayList;
        this.context = context;
        this.musclesListAdapteListener = musc;

    }

    @NonNull
    @Override
    public TrendingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        BaseItemBinding baseItemBinding =BaseItemBinding.inflate(layoutInflater,parent,false);
        return new TrendingViewHolder(baseItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TrendingViewHolder holder, @SuppressLint("RecyclerView") int position) {

        MovieResult tvResult = arrayList.get(position);
        holder.binding.setMoviePop(tvResult);
        holder.binding.executePendingBindings();
        holder.binding.movieImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pTrend = arrayList.indexOf(arrayList.get(position));
                movie_position = arrayList.get(position);
                musclesListAdapteListener.onClickItemTrending();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class TrendingViewHolder extends RecyclerView.ViewHolder {

        BaseItemBinding binding;
        public TrendingViewHolder(@NonNull BaseItemBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }

}
