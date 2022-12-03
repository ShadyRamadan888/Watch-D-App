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

public class PopularMoviesAdapter extends RecyclerView.Adapter<PopularMoviesAdapter.PopularMoviesViewHolder> {

    List<MovieResult> list;
    Context context;
    public static int p;
    public  MusclesListAdapteListener musclesListAdapteListener;
    public static MovieResult movie_position = new MovieResult();
    private int i=0;
    int backOrNot =-1;



    public PopularMoviesAdapter(java.util.List<MovieResult> list, Context context,MusclesListAdapteListener musc) {
        this.list = list;
        this.context = context;
        this.musclesListAdapteListener = musc;
    }

    @NonNull
    @Override
    public PopularMoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        BaseItemBinding binding = BaseItemBinding.inflate(layoutInflater,parent,false);
        return new PopularMoviesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularMoviesViewHolder holder, @SuppressLint("RecyclerView") int position) {

        MovieResult movieResult = list.get(position);
        holder.binding.setMoviePop(movieResult);
        holder.binding.executePendingBindings();
        holder.binding.movieImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                i++;
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (i == 1)
//                        {
                            p = list.indexOf(list.get(position));
                            movie_position = list.get(position);
                            musclesListAdapteListener.onClickItem();
//                        }
//                        i=0;
//                    }
//                },500);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PopularMoviesViewHolder extends RecyclerView.ViewHolder {

          BaseItemBinding binding;
        public PopularMoviesViewHolder(@NonNull BaseItemBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }


}
