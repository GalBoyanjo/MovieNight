package com.example.gal.mymovies.Entities;

import com.example.gal.mymovies.Interfaces.MoviesService;
import com.example.gal.mymovies.Rest.PopularMoviesListResult;
import com.example.gal.mymovies.Rest.PopularMoviesResult;

import java.util.ArrayList;

public class MovieModelConverter {

    public static ArrayList<MovieModel> convertResult(PopularMoviesListResult popularMoviesListResult) {

        ArrayList<MovieModel> result = new ArrayList<>();
        for (PopularMoviesResult popularMoviesResult : popularMoviesListResult.getPopularMoviesResults()) {
            result.add(new MovieModel(popularMoviesResult.getId(), popularMoviesResult.getTitle(),
                    MoviesService.POSTER_BASE_URL + popularMoviesResult.getPosterPath(),
                    MoviesService.BACKDROP_BASE_URL + popularMoviesResult.getBackdropPath(),
                    popularMoviesResult.getOverview(),
                    popularMoviesResult.getReleaseDate()));
        }

        return result;
    }
}
