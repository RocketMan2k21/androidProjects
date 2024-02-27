package com.bestdeveloper.pagingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.Toast;

import com.bestdeveloper.pagingapp.adapter.MovieAdapter;
import com.bestdeveloper.pagingapp.adapter.MovieLoadStateAdapter;
import com.bestdeveloper.pagingapp.databinding.ActivityMainBinding;
import com.bestdeveloper.pagingapp.service.GridSpace;
import com.bestdeveloper.pagingapp.service.MovieComparator;
import com.bestdeveloper.pagingapp.service.Utils;
import com.bestdeveloper.pagingapp.viewmodel.MovieViewModel;
import com.bumptech.glide.RequestManager;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private MovieViewModel movieViewModel;
    ActivityMainBinding binding;
    MovieAdapter movieAdapter;

    @Inject
    RequestManager requestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(Utils.API_KEY == null || Utils.API_KEY.isEmpty()){
            Toast.makeText(this, "ERROR IN API KEY", Toast.LENGTH_SHORT).show();
        }

        movieAdapter = new MovieAdapter(new MovieComparator(), requestManager);


        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);

        initRecyclerViewAndAdapter();

        // subsribe to paging data
        movieViewModel.moviePagingDataFlowable.subscribe(moviePagingData -> {
            movieAdapter.submitData(getLifecycle(), moviePagingData);
        });
    }

    private void initRecyclerViewAndAdapter() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        binding.recyclerViewMovies.setLayoutManager(gridLayoutManager);

        binding.recyclerViewMovies.addItemDecoration(new GridSpace(2, 20, true));

        binding.recyclerViewMovies.setAdapter(
                movieAdapter.withLoadStateFooter(
                        new MovieLoadStateAdapter(view ->{
                            movieAdapter.retry();
                        }))
        );

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return movieAdapter.getItemViewType(position) == MovieAdapter.LOADING_ITEM ? 1:2;
            }
        });
    }
}