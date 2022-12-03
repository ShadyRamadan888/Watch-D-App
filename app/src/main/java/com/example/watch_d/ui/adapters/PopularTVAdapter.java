package com.example.watch_d.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watch_d.databinding.TvBaseItemBinding;
import com.example.watch_d.pojo.TVResult;
import com.example.watch_d.utils.MusclesListAdapteListener;

import java.util.List;

public class PopularTVAdapter extends RecyclerView.Adapter<PopularTVAdapter.PopularTVViewHolder> {

    List<TVResult> arrayList ;
    Context context;
    public static TVResult movie_position = new TVResult();
    public static int pTV;
    public MusclesListAdapteListener musclesListAdapteListener;

    public PopularTVAdapter(List<TVResult> arrayList, Context context,MusclesListAdapteListener musc) {
        this.arrayList = arrayList;
        this.context = context;
        this.musclesListAdapteListener = musc;
    }

    @NonNull
    @Override
    public PopularTVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        TvBaseItemBinding binding =TvBaseItemBinding.inflate(layoutInflater,parent,false);
        return new PopularTVViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularTVViewHolder holder,@SuppressLint("RecyclerView") int position) {

        TVResult tvResult = arrayList.get(position);
        holder.binding.setTvData(tvResult);
        holder.binding.executePendingBindings();
        holder.binding.movieImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pTV = arrayList.indexOf(arrayList.get(position));
                movie_position = arrayList.get(position);
                musclesListAdapteListener.onClickItemTV();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class PopularTVViewHolder extends RecyclerView.ViewHolder {


        TvBaseItemBinding binding;
        public PopularTVViewHolder(@NonNull TvBaseItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}
