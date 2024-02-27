package com.bestdeveloper.pagingapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.LoadState;
import androidx.paging.LoadStateAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bestdeveloper.pagingapp.R;
import com.bestdeveloper.pagingapp.databinding.LoadStateItemBinding;

public class MovieLoadStateAdapter extends LoadStateAdapter<MovieLoadStateAdapter.LoadStateViewHolder> {

    private View.OnClickListener mRetryCallBack;

    public MovieLoadStateAdapter(View.OnClickListener mRetryCallBack) {
        this.mRetryCallBack = mRetryCallBack;
    }

    @Override
    public void onBindViewHolder(@NonNull LoadStateViewHolder loadStateViewHolder, @NonNull LoadState loadState) {
        loadStateViewHolder.bind(loadState);
    }

    @NonNull
    @Override
    public LoadStateViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, @NonNull LoadState loadState) {
        return new LoadStateViewHolder(viewGroup, mRetryCallBack);
    }

    public static class LoadStateViewHolder extends RecyclerView.ViewHolder{
        private Button retryBtn;
        private ProgressBar mProgressBar;
        private TextView mErrorMsg;

        public LoadStateViewHolder(@NonNull ViewGroup parent, @NonNull View.OnClickListener retryCallBack) {
            super(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.load_state_item, parent, false));

            LoadStateItemBinding binding = LoadStateItemBinding.bind(itemView);

            retryBtn = binding.retryButton;
            mProgressBar = binding.progressBar;
            mErrorMsg = binding.errorMsg;

        }


        public void bind(@NonNull LoadState loadState){
           if(loadState instanceof LoadState.Error){
               LoadState.Error loadStateError = (LoadState.Error) loadState;
               mErrorMsg.setText(loadStateError.getError().toString());
           }

           retryBtn.setVisibility(loadState instanceof LoadState.Error ? View.VISIBLE : View.GONE);
           mProgressBar.setVisibility(loadState instanceof LoadState.Error ? View.VISIBLE : View.GONE);
           mErrorMsg.setVisibility(loadState instanceof LoadState.Error ? View.VISIBLE : View.GONE);

        }
    }
}
