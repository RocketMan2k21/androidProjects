package com.bestdeveloper.pagingapp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bestdeveloper.pagingapp.R;
import com.bestdeveloper.pagingapp.databinding.SingleMovieItemBinding;
import com.bestdeveloper.pagingapp.model.Movie;
import com.bumptech.glide.RequestManager;

public class MovieAdapter extends PagingDataAdapter<Movie, MovieAdapter.MovieViewHolder> {

    public static final int LOADING_ITEM = 0;
    public static final int MOVIE_ITEM = 1;

    RequestManager glide;

    public MovieAdapter(@NonNull DiffUtil.ItemCallback<Movie> diffCallback, RequestManager glide) {
        super(diffCallback);
        this.glide = glide;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SingleMovieItemBinding singleMovieItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.single_movie_item,
                parent,
                false);

        return new MovieViewHolder(singleMovieItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie currentMovie = getItem(position);

        glide.load("https://image.tmdb.org/t/p/w500" + currentMovie.getPosterPath())
                .into(holder.movieItemBinding.imageViewMovie);

        Log.i("MRA", currentMovie.getVoteAverage() + "");
        holder.movieItemBinding.textViewRating.setText(String.valueOf(currentMovie.getVoteAverage()));
    }

    @Override
    public int getItemViewType(int position) {
        return getItemCount() == position ? MOVIE_ITEM : LOADING_ITEM ;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder{
        SingleMovieItemBinding movieItemBinding;

        public MovieViewHolder(SingleMovieItemBinding movieItemBinding) {
            super(movieItemBinding.getRoot());
            this.movieItemBinding = movieItemBinding;
        }
    }
}
