package com.example.watch_d.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watch_d.databinding.TopRatedBaseItemBinding;
import com.example.watch_d.pojo.MovieResult;
import com.example.watch_d.utils.MusclesListAdapteListener;

import java.util.List;

public class TopRatedMoviesAdapter extends RecyclerView.Adapter<TopRatedMoviesAdapter.TopRatedMoviesViewHolder> {

    List<MovieResult> list;
    Context context;
    public MusclesListAdapteListener musclesListAdapteListener;
    public static MovieResult movie_top_rated = new MovieResult();
    public static int pTopRatedMovies;

    public TopRatedMoviesAdapter( List<MovieResult> list,Context context, MusclesListAdapteListener musclesListAdapteListener) {
        this.list = list;
        this.context = context;
        this.musclesListAdapteListener = musclesListAdapteListener;
    }

    @NonNull
    @Override
    public TopRatedMoviesAdapter.TopRatedMoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        TopRatedBaseItemBinding binding = TopRatedBaseItemBinding.inflate(layoutInflater,parent,false);

        return new TopRatedMoviesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TopRatedMoviesAdapter.TopRatedMoviesViewHolder holder, @SuppressLint("RecyclerView") int position) {

        MovieResult movieResult = list.get(position);
        holder.binding.setTopRatedVariable(movieResult);
        holder.binding.executePendingBindings();
        holder.binding.movieImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pTopRatedMovies = list.indexOf(list.get(position));
                movie_top_rated = list.get(position);
                musclesListAdapteListener.onClickItemMoviesTopRated();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TopRatedMoviesViewHolder extends RecyclerView.ViewHolder {
        TopRatedBaseItemBinding binding;
        public TopRatedMoviesViewHolder(@NonNull TopRatedBaseItemBinding binding) {
            super(binding.getRoot());

            this.binding = binding;

        }
    }

    public void setUpdateData(List<MovieResult> list)
    {
        this.list= list;
        notifyDataSetChanged();
    }
    public List<MovieResult> getList()
    {
        return list;
    }
}
