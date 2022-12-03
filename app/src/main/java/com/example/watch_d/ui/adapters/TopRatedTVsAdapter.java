package com.example.watch_d.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watch_d.databinding.TopRatedBaseItemBinding;
import com.example.watch_d.databinding.TvBaseItemBinding;
import com.example.watch_d.pojo.TVResult;
import com.example.watch_d.utils.MusclesListAdapteListener;

import java.util.List;

public class TopRatedTVsAdapter extends RecyclerView.Adapter<TopRatedTVsAdapter.TopRatedTVsViewHolder>{

    List<TVResult> list;
    Context context;
    public MusclesListAdapteListener musclesListAdapteListener;
    public static TVResult tv_top_rated = new TVResult();
    public static int pTopRatedTV;

    public TopRatedTVsAdapter(List<TVResult> list, Context context, MusclesListAdapteListener musclesListAdapteListener) {
        this.list = list;
        this.context = context;
        this.musclesListAdapteListener = musclesListAdapteListener;
    }

    @NonNull
    @Override
    public TopRatedTVsAdapter.TopRatedTVsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        TvBaseItemBinding binding = TvBaseItemBinding.inflate(layoutInflater,parent,false);

        return new TopRatedTVsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TopRatedTVsAdapter.TopRatedTVsViewHolder holder, @SuppressLint("RecyclerView") int position) {

        TVResult tvResult =list.get(position);
        holder.binding.setTvData(tvResult);
        holder.binding.executePendingBindings();
        holder.binding.movieImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pTopRatedTV = list.indexOf(list.get(position));
                tv_top_rated=list.get(position);
                musclesListAdapteListener.onClickItemTVsTopRated();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TopRatedTVsViewHolder extends RecyclerView.ViewHolder {
        TvBaseItemBinding binding;
        public TopRatedTVsViewHolder(@NonNull TvBaseItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
