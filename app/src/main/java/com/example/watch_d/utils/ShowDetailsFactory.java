package com.example.watch_d.utils;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.watch_d.R;
import com.example.watch_d.ui.adapters.PopularMoviesAdapter;
import com.example.watch_d.ui.adapters.PopularTVAdapter;
import com.example.watch_d.ui.adapters.TopRatedMoviesAdapter;
import com.example.watch_d.ui.adapters.TopRatedTVsAdapter;
import com.example.watch_d.ui.adapters.TrendingMovieAdapter;
import com.example.watch_d.ui.fragments.HomeFragment;

public class ShowDetailsFactory extends Fragment implements MusclesListAdapteListener {

    private View view;
    private NavController navController;
    private FragmentManager fragmentManager;
    public ShowDetailsFactory(View view,FragmentManager fragmentManager) {
        this.fragmentManager=fragmentManager;
        this.view = view;
        navController = Navigation.findNavController(view);
    }
    @Override
    public void onClickItem() {
        Bundle result = new Bundle();
        result.putString("poster_img", PopularMoviesAdapter.movie_position.backdrop_path);
        result.putString("overview",PopularMoviesAdapter.movie_position.overview);
        result.putString("title",PopularMoviesAdapter.movie_position.title);
        result.putString("movieImage",PopularMoviesAdapter.movie_position.poster_path);
        result.putString("rating",String.valueOf(PopularMoviesAdapter.movie_position.vote_average));
        result.putString("id",String.valueOf(PopularMoviesAdapter.movie_position.id));
        result.putString("date",PopularMoviesAdapter.movie_position.release_date);
        fragmentManager.setFragmentResult("movieData",result);
        navController.navigate(R.id.action_homeFragment_to_movieDetailsFragment);
    }

    @Override
    public void onClickItemTV() {

        Bundle result = new Bundle();
        result.putString("poster_img", PopularTVAdapter.movie_position.backdrop_path);
        result.putString("overview",PopularTVAdapter.movie_position.overview);
        result.putString("title",PopularTVAdapter.movie_position.name);
        result.putString("movieImage",PopularTVAdapter.movie_position.poster_path);
        result.putString("rating",String.valueOf(PopularTVAdapter.movie_position.vote_average));
        result.putString("id",String.valueOf(PopularTVAdapter.movie_position.id));
        fragmentManager.setFragmentResult("tvData",result);
        navController.navigate(R.id.action_homeFragment_to_movieDetailsFragment);
    }

    @Override
    public void onClickItemTrending() {

        Bundle result = new Bundle();
        result.putString("poster_img", TrendingMovieAdapter.movie_position.backdrop_path);
        result.putString("overview",TrendingMovieAdapter.movie_position.overview);
        result.putString("title",TrendingMovieAdapter.movie_position.title);
        result.putString("movieImage",TrendingMovieAdapter.movie_position.poster_path);
        result.putString("rating",String.valueOf(TrendingMovieAdapter.movie_position.vote_average));
        result.putString("id",String.valueOf(TrendingMovieAdapter.movie_position.id));
        fragmentManager.setFragmentResult("TrendingMovieData",result);
        navController.navigate(R.id.action_homeFragment_to_movieDetailsFragment);
    }

    @Override
    public void onClickItemMoviesTopRated() {
        Bundle result = new Bundle();
        result.putString("poster_img", TopRatedMoviesAdapter.movie_top_rated.backdrop_path);
        result.putString("overview",TopRatedMoviesAdapter.movie_top_rated.overview);
        result.putString("title",TopRatedMoviesAdapter.movie_top_rated.title);
        result.putString("movieImage",TopRatedMoviesAdapter.movie_top_rated.poster_path);
        result.putString("rating",String.valueOf(TopRatedMoviesAdapter.movie_top_rated.vote_average));
        result.putString("id",String.valueOf(TopRatedMoviesAdapter.movie_top_rated.id));
        fragmentManager.setFragmentResult("TopRatedMovieData",result);
        navController.navigate(R.id.action_homeFragment_to_movieDetailsFragment);
    }

    @Override
    public void onClickItemTVsTopRated() {
        Bundle result = new Bundle();
        result.putString("poster_img", TopRatedTVsAdapter.tv_top_rated.backdrop_path);
        result.putString("overview",TopRatedTVsAdapter.tv_top_rated.overview);
        result.putString("title",TopRatedTVsAdapter.tv_top_rated.name);
        result.putString("movieImage",TopRatedTVsAdapter.tv_top_rated.poster_path);
        result.putString("rating",String.valueOf(TopRatedTVsAdapter.tv_top_rated.vote_average));
        result.putString("id",String.valueOf(TopRatedTVsAdapter.tv_top_rated.id));
        fragmentManager.setFragmentResult("TopRatedTVData",result);
        navController.navigate(R.id.action_homeFragment_to_movieDetailsFragment);

    }

}
