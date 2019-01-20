package com.example.gal.mymovies.Interfaces;

import com.example.gal.mymovies.Rest.PopularMoviesListResult;
import com.example.gal.mymovies.Rest.VideosListResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MoviesService {

    String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342";
    String BACKDROP_BASE_URL = "https://image.tmdb.org/t/p/w780";
    String YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v=";


    String BASE_URL = "https://api.themoviedb.org";
    /* base search image url */
    String BASE_API_URL = BASE_URL + "/3/";

    String POPULAR = "movie/popular";
    String apiKey = "6a4c2c4741d1d6d7aa11645789ba8922";
    String keyQuery= "?api_key=" + apiKey;

    String POPULAR_QUERY_PATH = POPULAR + keyQuery;

    String MOVIE_ID_KEY = "movie_id";
    String VIDEOS = "movie/{" + MOVIE_ID_KEY + "}/videos";

    String VIDEOS_QUERY_PATH = VIDEOS + keyQuery;

    @GET(POPULAR_QUERY_PATH)
    Call<PopularMoviesListResult> searchPopular();

    @GET(VIDEOS_QUERY_PATH)
    Call<VideosListResult> getVideos(@Path(MOVIE_ID_KEY) int movieId);

}
