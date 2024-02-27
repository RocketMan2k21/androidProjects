package com.bestdeveloper.pagingapp.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.bestdeveloper.pagingapp.model.Movie;
import com.bestdeveloper.pagingapp.paging.MoviePagingSource;

import io.reactivex.rxjava3.core.Flowable;
import kotlinx.coroutines.CoroutineScope;

public class MovieViewModel extends ViewModel {

    public Flowable<PagingData<Movie>> moviePagingDataFlowable; // data to be displayed;
    public MovieViewModel() {
        init();
    }

    private void init() {
        MoviePagingSource moviePagingSource = new MoviePagingSource();
        Pager<Integer, Movie> pager = new Pager( //pager , amount of data to display, and max data
                new PagingConfig(
                        20,
                        20,
                        false,
                        20,
                        20*499
                ),
                ()->moviePagingSource);

        //Flowable
        moviePagingDataFlowable = PagingRx.getFlowable(pager); //getting flowable from pager
        CoroutineScope coroutineScope = ViewModelKt.getViewModelScope(this); //coroutine helps to load data in chunks
        PagingRx.cachedIn(moviePagingDataFlowable, coroutineScope); //caching data by coroutine, taking care of data stream
    }
}
