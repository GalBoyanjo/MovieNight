package com.example.gal.mymovies;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gal.mymovies.Entities.MovieModel;

public class MovieDetailsFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_MOVIE = "MovieModel";

    private MovieModel movieModel;

    ImageView titleImg;
    ImageView detailImg;
    TextView movieName;
    TextView movieReDate;
    TextView movieOverviewDesc;

    Button movieTrailerBtn;

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

        titleImg.setImageResource(movieModel.getBackImageRes());
        detailImg.setImageResource(movieModel.getImageRes());
        movieName.setText(movieModel.getName());
        movieReDate.setText(movieModel.getReleaseDate());
        movieOverviewDesc.setText(movieModel.getOverview());


    }

    @Override
    public void onClick(View view) {
        if (movieModel == null) return;
        String trailerUrl = movieModel.getTrailerUrl();
        if (TextUtils.isEmpty(trailerUrl)) return;

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl));
        startActivity(browserIntent);
    }
}
