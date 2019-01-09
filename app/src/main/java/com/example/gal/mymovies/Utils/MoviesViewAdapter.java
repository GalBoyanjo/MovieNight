package com.example.gal.mymovies.Utils;

import android.app.Activity;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gal.mymovies.Entities.MovieModel;
import com.example.gal.mymovies.Interfaces.MovieItemCallback;
import com.example.gal.mymovies.R;

import java.util.ArrayList;
import java.util.Locale;

public class MoviesViewAdapter extends RecyclerView.Adapter<MoviesViewAdapter.ViewHolder> {

    private MovieItemCallback movieItemCallback;
    private Context context;
    private int layoutResourceId;
    private ArrayList<MovieModel> data = new ArrayList<>();
    public ArrayList<MovieModel> searchData = new ArrayList<>();

    private OnItemClicked onClick;

    public interface OnItemClicked {
        void onItemClick(int position);
    }

    public MoviesViewAdapter(Context context, int layoutResourceId, ArrayList<MovieModel> data,
                             MovieItemCallback movieItemCallback) {
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data.addAll(data);
        this.searchData.addAll(data);
        this.movieItemCallback = movieItemCallback;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImage;
        TextView tvTitle;
        TextView tvOverview;
        ConstraintLayout row;


        public ViewHolder(View view) {
            super(view);
            this.ivImage = view.findViewById(R.id.row_movie_iv);
            this.tvTitle = view.findViewById(R.id.row_movie_title_tv);
            this.tvOverview = view.findViewById(R.id.row_movie_content_tv);
            this.row = view.findViewById(R.id.movie_row_constraint_layout);

        }
    }

    @Override
    public MoviesViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View row = inflater.inflate(layoutResourceId, parent, false);
        ViewHolder viewHolder = new ViewHolder(row);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final MovieModel movieModel = searchData.get(position);

        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movieItemCallback.movieSelcted(movieModel, position);

            }
        });



        try {
            holder.ivImage.setImageResource(movieModel.getImageRes());
            holder.tvTitle.setText(movieModel.getName());
            holder.tvOverview.setText(movieModel.getOverview());

        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    @Override
    public int getItemCount() {
        return searchData.size();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        searchData.clear();
        if (charText.length() == 0) {
            searchData.addAll(data);
        } else {
            for (MovieModel movieModel : data) {
                if (movieModel.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    searchData.add(movieModel);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void setOnClick(OnItemClicked onClick)
    {
        this.onClick=onClick;
    }


}