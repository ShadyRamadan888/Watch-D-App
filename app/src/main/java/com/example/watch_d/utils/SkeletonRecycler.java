package com.example.watch_d.utils;

import androidx.recyclerview.widget.RecyclerView;

import com.faltenreich.skeletonlayout.Skeleton;
import com.faltenreich.skeletonlayout.SkeletonLayoutUtils;

public class SkeletonRecycler {

    private Skeleton skeleton;
    private RecyclerView recyclerView;
    private int layout;

    public SkeletonRecycler(Skeleton skeleton, RecyclerView recyclerView, int layout) {
        this.skeleton = skeleton;
        this.recyclerView = recyclerView;
        this.layout = layout;

        this.skeleton = (Skeleton) SkeletonLayoutUtils.applySkeleton(recyclerView,layout);

    }

    public Skeleton getSkeleton() {
        return skeleton;
    }

    public void setSkeleton(Skeleton skeleton) {
        this.skeleton = skeleton;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public int getLayout() {
        return layout;
    }

    public void setLayout(int layout) {
        this.layout = layout;
    }
}
