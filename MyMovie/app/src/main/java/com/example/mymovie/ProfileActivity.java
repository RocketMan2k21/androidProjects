package com.example.mymovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.mymovie.databinding.ActivityProfileBinding;
import com.example.mymovie.db.entity.Profile;

public class ProfileActivity extends AppCompatActivity {

    ActivityProfileBinding binding;
    Profile profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profile = new Profile();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        binding.setProfile(profile);
    }
}