package com.example.gal.mymovies.Utils;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gal.mymovies.Entities.MovieModel;
import com.example.gal.mymovies.Interfaces.MovieItemCallback;
import com.example.gal.mymovies.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesViewAdapter extends RecyclerView.Adapter<MoviesViewAdapter.ViewHolder> {

    private MovieItemCallback movieItemCallback;
    private List<MovieModel> moviesData;

    private Picasso picasso;


    public MoviesViewAdapter(List<MovieModel> moviesData, MovieItemCallback movieItemCallback) {
        this.moviesData = moviesData;
        this.movieItemCallback = movieItemCallback;
        this.picasso = Picasso.get();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.bind(moviesData.get(position));
    }

    @Override
    public int getItemCount() {
        return moviesData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView ivImage;
        public final TextView tvTitle;
        public final TextView tvOverview;

        public ViewHolder(View view) {
            super(view);
            ivImage = view.findViewById(R.id.row_movie_iv);
            tvTitle = view.findViewById(R.id.row_movie_title_tv);
            tvOverview = view.findViewById(R.id.row_movie_overview_tv);
            view.setOnClickListener(this);
        }

        public void bind(MovieModel movieModel) {
            picasso.load(movieModel.getImageUri())
                    .into(ivImage, new Callback() {
                        @Override
                        public void onSuccess() {
                            Log.i("onSuccess", "onSuccess");
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.d("onError", "onError() called with: e = [" + e + "]");
                        }
                    });
            tvTitle.setText(movieModel.getName());
            tvOverview.setText(movieModel.getOverview());
        }

        @Override
        public void onClick(View view) {
            if (movieItemCallback == null) return;
            movieItemCallback.movieSelected(getAdapterPosition());
        }
    }
}