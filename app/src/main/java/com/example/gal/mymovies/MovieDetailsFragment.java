package com.example.gal.mymovies;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.RotateDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gal.mymovies.Entities.MovieModel;
import com.example.gal.mymovies.Interfaces.MoviesService;
import com.example.gal.mymovies.Rest.VideosListResult;
import com.example.gal.mymovies.Rest.VideosResult;
import com.example.gal.mymovies.Rest.RestClient;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_MOVIE = "MovieModel";

    private MovieModel movieModel;

    private ImageView titleImg;
    private ImageView detailImg;
    private TextView movieName;
    private TextView movieReDate;
    private TextView movieOverviewDesc;

    Button movieTrailerBtn;

    private Picasso picasso;

    public MovieDetailsFragment() { }

    public static MovieDetailsFragment newInstance(MovieModel movieModel) {

        MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();
        Bundle bundle = new Bundle();
//        bundle.putString(ARGS_MESSAGE, message);
        bundle.putParcelable(ARG_MOVIE, movieModel);
        movieDetailsFragment.setArguments(bundle);


        return movieDetailsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        picasso = Picasso.get();
        if (getArguments() != null) {
            movieModel = getArguments().getParcelable(ARG_MOVIE);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_movie_details_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        titleImg = view.findViewById(R.id.title_img);
        detailImg = view.findViewById(R.id.detail_img);
        movieName = view.findViewById(R.id.movie_name);
        movieReDate = view.findViewById(R.id.movie_date);
        movieOverviewDesc = view.findViewById(R.id.overview_description);

        movieTrailerBtn =  view.findViewById(R.id.movie_trailer_btn);
        movieTrailerBtn.setOnClickListener(this);

        setMovie();
    }

    private void setMovie(){
        if(movieModel == null) return;

        picasso.load(movieModel.getImageUri()).into(titleImg);
        picasso.load(movieModel.getBackImageUri()).into(detailImg);

        movieName.setText(movieModel.getName());
        movieReDate.setText(movieModel.getReleaseDate());
        movieOverviewDesc.setText(movieModel.getOverview());

    }

    @Override
    public void onClick(View view) {
        if (movieModel == null) return;

        setButtonLoadingStatus();

        MoviesService moviesService = RestClient.getMovieServiceInstance();
        moviesService.getVideos(movieModel.getMovieId())
                .enqueue(new Callback<VideosListResult>() {
                    @Override
                    public void onResponse(Call<VideosListResult> call, Response<VideosListResult> response) {
                        VideosListResult videosListResult = response.body();
                        if (videosListResult != null){
                            List<VideosResult> results = videosListResult.getResults();
                            if (results != null && !results.isEmpty()){
                                String trailerUrl = MoviesService.YOUTUBE_BASE_URL + results.get(0).getKey();
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl));
                                startActivity(browserIntent);
                            }
                        }
                        resetButtonStatus();
                    }

                    @Override
                    public void onFailure(Call<VideosListResult> call, Throwable t) {
                        Toast.makeText(getContext(), R.string.something_went_wrong_text, Toast.LENGTH_SHORT).show();
                        resetButtonStatus();
                    }
                });

    }

    private void setButtonLoadingStatus() {
        Context context = getContext();
        if (context == null) {
            return;
        }
        RotateDrawable rotateDrawable = (RotateDrawable) ContextCompat.getDrawable(context, R.drawable.progress_animation);
        ObjectAnimator anim = ObjectAnimator.ofInt(rotateDrawable, "level", 0, 10000);
        anim.setDuration(1000);
        anim.setRepeatCount(ValueAnimator.INFINITE);
        anim.start();
        movieTrailerBtn.setText(R.string.details_loading_trailer_text);
        movieTrailerBtn.setCompoundDrawablesWithIntrinsicBounds(rotateDrawable, null, null, null);
    }

    private void resetButtonStatus() {
        movieTrailerBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        movieTrailerBtn.setText(R.string.details_trailer_text);
    }
}
