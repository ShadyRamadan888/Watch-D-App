package com.example.watch_d.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.watch_d.R;
import com.example.watch_d.database.FavouriteEntity;
import com.example.watch_d.pojo.cast_and_crew.Cast;
import com.example.watch_d.ui.ShowVidActivity;
import com.example.watch_d.ui.activities.Favourites;
import com.example.watch_d.ui.adapters.CastAdapter;
import com.example.watch_d.ui.viewModels.CastViewModel;
import com.example.watch_d.utils.SkeletonRecycler;
import com.faltenreich.skeletonlayout.Skeleton;

import java.util.List;


public class MovieDetailsFragment extends Fragment  {

    ImageView poster,movieImage,playImage;
    TextView overview,title;
    RatingBar ratingBar;
    Toolbar toolbar;
    int isClicked = 0;

    private static FavouriteEntity entity;
    private int idPopMovie = 0;
    private int idPopTV = 0;
    private int idTrendMovie = 0;
    private int idTopRatedMovies=0;
    private int idTopRatedTV=0;

    private String popMovie;
    private String popTV;
    private String trendMovie;
    private String topRatedMovie;
    private String topRatedTVs;
    private CastViewModel  castViewModel = new CastViewModel();
    private CastAdapter castAdapter;
    private List<Cast> CastList;
    private RecyclerView castRecycler;
    private Skeleton skeletonCast;
    private ImageView bookmark;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_movie_details, container, false);
        toolbar = v.findViewById(R.id.toolbar);
        poster = v.findViewById(R.id.poster_img);
        overview = v.findViewById(R.id.overview);
        movieImage = v.findViewById(R.id.movie_img);
        title = v.findViewById(R.id.movie_title_details);
        ratingBar = v.findViewById(R.id.rating);
        playImage = v.findViewById(R.id.play_vid_image);
        castRecycler=v.findViewById(R.id.cast_recycle);
        skeletonCast = v.findViewById(R.id.skeleton_cast);
        bookmark = v.findViewById(R.id.bookmark);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //setting recycle layoutManager
        castRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        castRecycler.setHasFixedSize(true);

        //Getting movie details from home fragment
        settingToolbar();
        getPopMovieDetails();
        getPopTVDetails();
        getTrendMovieDetails();
        getTopRatedMovieDetails();
        getTopRatedTVsDetails();


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        //NavController navController = Navigation.findNavController(view);
        playImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent Solution//
                Intent intent = new Intent(getActivity(), ShowVidActivity.class);
                if(idPopMovie>0)
                {
                    intent.putExtra("id",idPopMovie);
                    popMovie = "POPULAR_MOVIES";
                    intent.putExtra("type",popMovie);
                    startActivity(intent);
                }
                else if(idTrendMovie>0)
                {
                    intent.putExtra("id",idTrendMovie);
                    trendMovie = "TREND_MOVIES";
                    intent.putExtra("type",trendMovie);
                    startActivity(intent);
                }
                else if(idPopTV>0)
                {
                    popTV = "POPULAR_TV";
                    intent.putExtra("id",idPopTV);
                    intent.putExtra("type",popTV);
                    startActivity(intent);
                }
                else if(idTopRatedMovies>0)
                {
                    topRatedMovie="TOP_RATED_MOVIE";
                    intent.putExtra("id",idTopRatedMovies);
                    intent.putExtra("type",topRatedMovie);
                    startActivity(intent);
                }
                else if(idTopRatedTV>0)
                {
                    topRatedTVs = "TOP_RATED_TV";
                    intent.putExtra("id",idTopRatedTV);
                    intent.putExtra("type",topRatedTVs);
                    startActivity(intent);
                }
            }
        });

        SkeletonRecycler skeleton = new SkeletonRecycler(skeletonCast,castRecycler,R.layout.cast_item);
        skeleton.getSkeleton().showSkeleton();

    }
    public void getPopMovieDetails()
    {
        getParentFragmentManager().setFragmentResultListener("movieData", this, new FragmentResultListener() {

            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String date = result.getString("date");
                String over = result.getString("overview");
                overview.setText(over);
                String movie_title = result.getString("title");
                title.setText(movie_title);
                String rating = result.getString("rating");
                ratingBar.setRating(Float.valueOf(rating)/2);
                String movie_poster = result.getString("poster_img");
                Glide.with(getActivity()).load("https://image.tmdb.org/t/p/original"+movie_poster).into(poster);
                String movie_image = result.getString("movieImage");
                Glide.with(getActivity()).load("https://image.tmdb.org/t/p/w500"+movie_image).into(movieImage);
                idPopMovie = Integer.parseInt(result.getString("id"));
                getMovieCast(idPopMovie);


                bookmark.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        entity = new FavouriteEntity(idPopMovie,movie_image,movie_title,date,Float.valueOf(rating)/2);
                        bookmark.setBackgroundResource(R.drawable.fav_selected);
                    }
                });
            }
        });
    }

    public void getPopTVDetails()
    {
        getParentFragmentManager().setFragmentResultListener("tvData", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {

                String over = result.getString("overview");
                String movie_title = result.getString("title");
                String rating = result.getString("rating");
                String movie_poster = result.getString("poster_img");
                String movie_image = result.getString("movieImage");
                overview.setText(over);
                title.setText(movie_title);
                ratingBar.setRating(Float.valueOf(rating)/2);
                Glide.with(getActivity()).load("https://image.tmdb.org/t/p/original"+movie_poster).into(poster);
                Glide.with(getActivity()).load("https://image.tmdb.org/t/p/w500"+movie_image).into(movieImage);
                idPopTV = Integer.parseInt(result.getString("id"));
                getTVCast(idPopTV);
            }
        });
    }


    public void getTrendMovieDetails()
    {
        getParentFragmentManager().setFragmentResultListener("TrendingMovieData", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String over = result.getString("overview");
                overview.setText(over);
                String movie_title = result.getString("title");
                title.setText(movie_title);
                String rating = result.getString("rating");
                ratingBar.setRating(Float.valueOf(rating)/2);
                String movie_poster = result.getString("poster_img");
                Glide.with(getActivity()).load("https://image.tmdb.org/t/p/original"+movie_poster).into(poster);
                String movie_image = result.getString("movieImage");
                Glide.with(getActivity()).load("https://image.tmdb.org/t/p/w500"+movie_image).into(movieImage);
                idTrendMovie = Integer.parseInt(result.getString("id"));
                getMovieCast(idTrendMovie);
            }
        });
    }

    public void getTopRatedMovieDetails()
    {
        getParentFragmentManager().setFragmentResultListener("TopRatedMovieData", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String over = result.getString("overview");
                overview.setText(over);
                String movie_title = result.getString("title");
                title.setText(movie_title);
                String rating = result.getString("rating");
                ratingBar.setRating(Float.valueOf(rating)/2);
                String movie_poster = result.getString("poster_img");
                Glide.with(getActivity()).load("https://image.tmdb.org/t/p/original"+movie_poster).into(poster);
                String movie_image = result.getString("movieImage");
                Glide.with(getActivity()).load("https://image.tmdb.org/t/p/w500"+movie_image).into(movieImage);
                idTopRatedMovies = Integer.parseInt(result.getString("id"));
                getMovieCast(idTopRatedMovies);
            }
        });
    }

    public void getTopRatedTVsDetails()
    {

        getParentFragmentManager().setFragmentResultListener("TopRatedTVData", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {

                String over = result.getString("overview");
                overview.setText(over);
                String movie_title = result.getString("title");
                title.setText(movie_title);
                String rating = result.getString("rating");
                ratingBar.setRating(Float.valueOf(rating)/2);
                String movie_poster = result.getString("poster_img");
                Glide.with(getActivity()).load("https://image.tmdb.org/t/p/original"+movie_poster).into(poster);
                String movie_image = result.getString("movieImage");
                Glide.with(getActivity()).load("https://image.tmdb.org/t/p/w500"+movie_image).into(movieImage);
                idTopRatedTV = Integer.parseInt(result.getString("id"));
                getMovieCast(idTopRatedTV);


            }
        });

    }
    public void settingToolbar()
    {
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_24);
        getPopMovieDetails();
    }

    public void getMovieCast(int id)
    {
        castViewModel.getMovieCast(id);
        castViewModel.getMovieCastLive().observe(getViewLifecycleOwner(), new Observer<List<Cast>>() {
            @Override
            public void onChanged(List<Cast> casts) {

                try {
                    castAdapter = new CastAdapter(casts,getActivity());
                    castRecycler.setAdapter(castAdapter);
                }
                catch (Exception e)
                {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void getTVCast(int id)
    {
        castViewModel.getTVCast(id);
        castViewModel.getTVCastLive().observe(getViewLifecycleOwner(), new Observer<List<Cast>>() {
            @Override
            public void onChanged(List<Cast> casts) {

                try {
                    castAdapter = new CastAdapter(casts,getActivity());
                    castRecycler.setAdapter(castAdapter);
                }
                catch (Exception e)
                {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public static FavouriteEntity getEntity()
    {
        return entity;
    }

}