package com.example.mymovie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymovie.R;
import com.example.mymovie.db.entity.Profile;

import java.util.ArrayList;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.MyViewHolder> {

    ArrayList<Profile> profiles;
    Context context;

    public ProfileAdapter(ArrayList<Profile> profiles, Context context) {
        this.profiles = profiles;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView profile_login;
        private TextView profile_email;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            profile_login = itemView.findViewById(R.id.profile_login);
            profile_email = itemView.findViewById(R.id.profile_email);
        }


    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(context)
                .inflate(R.layout.profile_item, parent  , false);

        return new MyViewHolder(myView);


    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Profile profile = profiles.get(position);
        holder.profile_email.setText(profile.getEmail());
        holder.profile_login.setText(profile.getLogin());
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }


}
