package com.example.mymovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.example.mymovie.databinding.ActivitySignInBinding;
import com.example.mymovie.db.entity.Profile;

public class SignInActivity extends AppCompatActivity {

    ActivitySignInBinding binding;
    SignInActivityClickHandlers clickHandlers;

    Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in);
        clickHandlers = new SignInActivityClickHandlers(this);
        binding.setClickHandler(clickHandlers);

        profile = new Profile();
        binding.setProfile(profile);



    }


    public class SignInActivityClickHandlers{
        Context context;

        public SignInActivityClickHandlers(Context context) {
            this.context = context;
        }

        public void onSignClick(View view){

        }
    }
}