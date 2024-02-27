package com.example.mymovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.mymovie.databinding.ActivitySignUpBinding;
import com.example.mymovie.db.entity.Profile;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding binding;
    SignUpActivityClickHandlers clickHandler;
     Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Data Binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        clickHandler = new SignUpActivityClickHandlers(this);
        binding.setClickHandler(clickHandler);

        profile = new Profile();
        binding.setProfile(profile);

    }


    public class SignUpActivityClickHandlers{
        Context context;

        public SignUpActivityClickHandlers(Context context) {
            this.context = context;
        }

        public void onSignClick(View view){
            if(TextUtils.isEmpty(profile.getLogin())){
                Toast.makeText(context, "Please enter your login", Toast.LENGTH_SHORT).show();
            }else if(TextUtils.isEmpty(profile.getEmail())){
                Toast.makeText(context, "Please enter your email", Toast.LENGTH_SHORT).show();

            }else if(TextUtils.isEmpty(profile.getPassword())){
                Toast.makeText(context, "Please enter your password", Toast.LENGTH_SHORT).show();

            }else{
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("LOGIN", profile.getLogin());
                i.putExtra("EMAIL", profile.getEmail());
                i.putExtra("PASSWORD", profile.getPassword());

                setResult(RESULT_OK, i);
                finish();
            }
        }
    }
}