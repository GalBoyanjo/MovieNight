package com.example.gal.mymovies.Interfaces;

import com.example.gal.mymovies.Entities.MovieModel;

public interface MovieItemCallback {
    void movieSelcted(MovieModel movieModel, int position);
}
