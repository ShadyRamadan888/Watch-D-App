package com.example.watch_d.ui.fragments;


import static androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy.ALLOW;
import static androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.watch_d.R;
import com.example.watch_d.pojo.MovieResult;
import com.example.watch_d.pojo.TVResult;
import com.example.watch_d.ui.activities.Favourites;
import com.example.watch_d.ui.adapters.PopularMoviesAdapter;
import com.example.watch_d.ui.adapters.PopularTVAdapter;
import com.example.watch_d.ui.adapters.SliderAdapter;
import com.example.watch_d.ui.adapters.TopRatedMoviesAdapter;
import com.example.watch_d.ui.adapters.TopRatedTVsAdapter;
import com.example.watch_d.ui.adapters.TrendingMovieAdapter;
import com.example.watch_d.ui.viewModels.ImagesSliderViewModel;
import com.example.watch_d.ui.viewModels.TopRatedMovieViewModel;
import com.example.watch_d.utils.MusclesListAdapteListener;
import com.example.watch_d.ui.viewModels.PopularMovieViewModel;
import com.example.watch_d.ui.viewModels.PopularTVViewModel;
import com.example.watch_d.utils.ShowDetailsFactory;
import com.example.watch_d.utils.SkeletonRecycler;
import com.example.watch_d.ui.viewModels.TrendingMoviesViewMode;
import com.faltenreich.skeletonlayout.Skeleton;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.List;


public class HomeFragment extends Fragment {


    ScrollView scrollView;
    boolean tvClicked=false;
    boolean movieClicked=false;

    private RecyclerView popularRecyclerView;
    private RecyclerView trendingRecyclerView;
    private RecyclerView topRatedRecyclerView;
    private  PopularMovieViewModel popularMovieViewModel = new PopularMovieViewModel();
    private final ImagesSliderViewModel imagesSliderViewModel = new ImagesSliderViewModel();
    private final TrendingMoviesViewMode trendingMoviesViewMode = new TrendingMoviesViewMode();
    private final PopularTVViewModel popularTVViewModel = new PopularTVViewModel();
    private  TopRatedMovieViewModel topRatedMovieViewModel;
    private PopularMoviesAdapter adapterPopMovies;
    private PopularTVAdapter popularTVAdapter;
    private TopRatedTVsAdapter topRatedTVsAdapter;
    private TopRatedMoviesAdapter topRatedMoviesAdapter;
    private SliderAdapter sliderAdapter;
    private SliderView sliderView;
    private TrendingMovieAdapter trendingMovieAdapter;
    private TextView popMovies,popTV,todayTextView,weekTextView;

    private Skeleton skeleton;
    private Skeleton skeleton22;
    private Skeleton skeleton3;
    private SkeletonRecycler skeletonObject;
    MusclesListAdapteListener adapteListener;

    private ShowDetailsFactory showDetailsFactory;

    private ImageView fav_btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        topRatedMovieViewModel = new ViewModelProvider(getViewModelStore(),getDefaultViewModelProviderFactory()).get(TopRatedMovieViewModel.class);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //Popular RecyclerView
        bindingVariables(view);
        popularRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        popularRecyclerView.setHasFixedSize(true);

        //Trending RecyclerView
        trendingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        trendingRecyclerView.setHasFixedSize(true);


        //topRated RecyclerView
        topRatedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        topRatedRecyclerView.setHasFixedSize(true);

        //Setting Default Background for selection buttons and Calling fun to switch background
        popMovies.setBackground(getResources().getDrawable(R.drawable.btn_background));
        popBtnBackgroundClick();

        todayTextView.setBackground(getResources().getDrawable(R.drawable.btn_background));
        trendingBtnBackgroundClick();


        //Calling Skeleton method and sending *skeleton && skeleton Object && RecyclerViews && item_Layout*
        Skeleton(skeleton,skeletonObject,popularRecyclerView,R.layout.base_item);
        Skeleton(skeleton22,skeletonObject,trendingRecyclerView,R.layout.base_item);


        fav_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Favourites.class);
                startActivity(intent);
            }
        });

        getSliderImages();
        getTopRatedMovies();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        showDetailsFactory = new ShowDetailsFactory(view,getParentFragmentManager());


        trendingMovieAdapter = new TrendingMovieAdapter(getContext(),showDetailsFactory);

        popMovies.getText();
        popTV.getText();

        getPopularMovie();
        getTrendingDayMovies();

    }

    public void bindingVariables(View view)
    {
        popularRecyclerView = view.findViewById(R.id.recyclerViewPopular);
        trendingRecyclerView = view.findViewById(R.id.recyclerViewTrending);

        sliderView = view.findViewById(R.id.imageSliderBloodTest);

        popMovies = view.findViewById(R.id.pop_movies_tv);
        popTV = view.findViewById(R.id.pop_TV_tv);

        todayTextView = view.findViewById(R.id.today_tv);
        weekTextView = view.findViewById(R.id.week_tv);

        skeleton = view.findViewById(R.id.skeletonLayout);
        skeleton22 = view.findViewById(R.id.skeleton2);

        scrollView = view.findViewById(R.id.scrollView);

        skeleton3 = view.findViewById(R.id.skeleton3);
        topRatedRecyclerView = view.findViewById(R.id.recyclerViewTopRated);

        fav_btn = view.findViewById(R.id.fav_btn);
    }

    public void getPopularMovie() {

        popularMovieViewModel.getPopularMovies();

        popularMovieViewModel.getPopularLiveData().observe(getViewLifecycleOwner(), new Observer<List<MovieResult>>() {
            @Override
            public void onChanged(List<MovieResult> movieResults) {

                adapterPopMovies = new PopularMoviesAdapter(movieResults,getContext(),showDetailsFactory);
                adapterPopMovies.notifyDataSetChanged();
                popularRecyclerView.setAdapter(adapterPopMovies);

            }
        });
    }


    public void getPopularTV()
    {

        popularTVViewModel.getPopularTV();

        popularTVViewModel.getLiveData().observe(this, new Observer<List<TVResult>>() {
            @Override
            public void onChanged(List<TVResult> tvResults) {
                popularTVAdapter = new PopularTVAdapter(tvResults,getContext(), showDetailsFactory);
                popularTVAdapter.notifyDataSetChanged();
                popularRecyclerView.setAdapter(popularTVAdapter);
            }
        });
    }

    public void popBtnBackgroundClick()
    {

        popMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popMovies.setBackground(getResources().getDrawable(R.drawable.btn_background));
                popTV.setBackgroundResource(0);
                getPopularMovie();
                movieClicked = true;
                tvClicked = false;
            }
        });
        popTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popTV.setBackground(getResources().getDrawable(R.drawable.btn_background));
                popMovies.setBackgroundResource(0);
                getPopularTV();
                movieClicked = false;
                tvClicked = true;
            }
        });

    }

    public void getSliderImages() {
        imagesSliderViewModel.getSliderLiveData().observe(getViewLifecycleOwner(), new Observer<List<MovieResult>>() {
            @Override
            public void onChanged(List<MovieResult> movieResults) {

                sliderAdapter = new SliderAdapter(movieResults, getContext());
                sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
                sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
                sliderView.startAutoCycle();
                sliderView.setSliderAdapter(sliderAdapter);
            }
        });
        imagesSliderViewModel.getSliderImages();
    }

    public void getTrendingDayMovies() {

        trendingMoviesViewMode.getTrendingMovies();

        trendingMoviesViewMode.getTrendingLiveData().observe(getViewLifecycleOwner(), new Observer<List<MovieResult>>() {
            @Override
            public void onChanged(List<MovieResult> movieResults) {
                trendingMovieAdapter.setTrendingList(movieResults);
                trendingRecyclerView.setAdapter(trendingMovieAdapter);
            }
        });
    }

    public void trendingBtnBackgroundClick()
    {
        todayTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todayTextView.setBackground(getResources().getDrawable(R.drawable.btn_background));
                getTrendingDayMovies();
                weekTextView.setBackgroundResource(0);
            }
        });

        weekTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weekTextView.setBackground(getResources().getDrawable(R.drawable.btn_background));
                getTrendingWeekMovies();
                todayTextView.setBackgroundResource(0);
            }
        });
    }


    public void getTopRatedMovies()
    {
        topRatedMovieViewModel.getTopRatedMovies();
        topRatedMovieViewModel.getTopRatedMutableMoviesData().observe(getViewLifecycleOwner(), new Observer<List<MovieResult>>() {
            @Override
            public void onChanged(List<MovieResult> movieResults) {
                topRatedMoviesAdapter = new TopRatedMoviesAdapter(movieResults,getContext(),showDetailsFactory);
                topRatedRecyclerView.setAdapter(topRatedMoviesAdapter);
            }
        });
        //topRatedMoviesAdapter.setStateRestorationPolicy(ALLOW);
    }

    public void getTrendingWeekMovies()
    {
        trendingMoviesViewMode.getTrendingWeekMovies();

        trendingMoviesViewMode.getTrendingWeekLiveData().observe(getViewLifecycleOwner(), new Observer<List<MovieResult>>() {
            @Override
            public void onChanged(List<MovieResult> movieResults) {
                trendingMovieAdapter.setTrendingList(movieResults);
                trendingRecyclerView.setAdapter(trendingMovieAdapter);
            }
        });
    }

    public  void Skeleton(Skeleton skeleton,SkeletonRecycler skeletonObject,RecyclerView recyclerView,int layout) {
        skeletonObject = new SkeletonRecycler(skeleton,recyclerView,layout);
        skeletonObject.getSkeleton().showSkeleton();
    }

}