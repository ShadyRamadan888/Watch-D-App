package com.example.watch_d.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.watch_d.R;
import com.example.watch_d.pojo.cast_and_crew.Cast;

import java.util.ArrayList;
import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastViewHolder> {

    private List<Cast> arrayList;
    private Context context;
    public static Cast cast_position = new Cast();
    public static int CastP;
    public CastAdapter(List<Cast> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CastViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cast_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CastViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.castName.setText(arrayList.get(position).name);
        Glide.with(context).load("https://image.tmdb.org/t/p/w500/"+arrayList.get(position).profile_path).into(holder.castImage);
        CastP = position;
        cast_position = arrayList.get(position);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CastViewHolder extends RecyclerView.ViewHolder {

        TextView castName;
        ImageView castImage;
        public CastViewHolder(@NonNull View itemView) {
            super(itemView);
            castImage = itemView.findViewById(R.id.cast_image);
            castName = itemView.findViewById(R.id.cast_name);
        }
    }

}
