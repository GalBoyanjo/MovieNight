package com.example.gal.mymovies;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.gal.mymovies.Entities.MovieContent;
import com.example.gal.mymovies.Entities.MovieModelConverter;
import com.example.gal.mymovies.Interfaces.MovieItemCallback;
import com.example.gal.mymovies.Interfaces.MoviesService;
import com.example.gal.mymovies.Rest.PopularMoviesListResult;
import com.example.gal.mymovies.Rest.RestClient;
import com.example.gal.mymovies.Utils.DetailsActivity;
import com.example.gal.mymovies.Utils.MoviesViewAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesActivity extends AppCompatActivity implements MovieItemCallback {

    private RecyclerView recyclerView;

    private View progressBar;

    private static final String COUNTER_FRAGMENT_TAG = "counter_fragment";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        recyclerView = findViewById(R.id.movies_recycle_view);
        progressBar = findViewById(R.id.main_progress);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadMovies();


    }

    @Override
    public void movieSelected(int position) {
        if (position < 0 || position >= MovieContent.MOVIES.size()) return;

        Intent intent = new Intent(MoviesActivity.this, DetailsActivity.class);
        intent.putExtra(DetailsActivity.EXTRA_ITEM_POSITION, position);
        startActivity(intent);

    }

    private void loadMovies() {
        MovieContent.clear();
        progressBar.setVisibility(View.VISIBLE);
        MoviesService moviesService = RestClient.getMovieServiceInstance();
        moviesService.searchPopular().enqueue(new Callback<PopularMoviesListResult>() {
            @Override
            public void onResponse(@NonNull Call<PopularMoviesListResult> call, @NonNull Response<PopularMoviesListResult> response) {
                Log.i("response", "response");
                progressBar.setVisibility(View.GONE);
                if(response.code() == 200){
                    MovieContent.MOVIES.addAll(MovieModelConverter.convertResult(response.body()));
                    recyclerView.setAdapter(new MoviesViewAdapter(MovieContent.MOVIES, MoviesActivity.this));
                }
            }

            @Override
            public void onFailure(Call<PopularMoviesListResult> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.i("failure", "failure");
                Toast.makeText(MoviesActivity.this, R.string.something_went_wrong_text, Toast.LENGTH_SHORT).show();
            }
        });

    }

    // Inflate the menu; this adds items to the action bar if it is present.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_async_task: {
                CounterFragment counterFragment = CounterFragment.newInstance("async task");
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.movies_activity_main_frame, counterFragment, COUNTER_FRAGMENT_TAG)
                        .commit();
            }
            case R.id.action_thread_handler: {

            }
        }
        return super.onOptionsItemSelected(item);
    }

}