package com.example.watch_d.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.watch_d.R;
import com.example.watch_d.pojo.MovieResult;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderViewHolder> {

    List<MovieResult> arrayList ;
    Context context;

    public SliderAdapter(List<MovieResult> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public SliderAdapter.SliderViewHolder onCreateViewHolder(ViewGroup parent) {
        return new SliderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item, parent, false));

    }

    @Override
    public void onBindViewHolder(SliderAdapter.SliderViewHolder viewHolder, int position) {

        Glide.with(context).load("https://image.tmdb.org/t/p/original/"+arrayList.get(position).backdrop_path).into(viewHolder.imageView);

    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    public class SliderViewHolder extends ViewHolder {
        ImageView imageView;
        public SliderViewHolder(View itemView) {
            super(itemView);
            imageView  = itemView.findViewById(R.id.imageItem);
        }
    }
}
