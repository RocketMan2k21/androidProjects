package com.example.mymovie;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mymovie.adapter.ProfileAdapter;
import com.example.mymovie.db.entity.Profile;
import com.example.mymovie.db.entity.ProfileDatabase;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    //Buttons: Sign In/Up
    Button signInBtn;
    Button signUpBtn;

    RecyclerView recyclerView;
    ProfileAdapter profileAdapter;
    ProfileDatabase profileDatabase;
    ArrayList<Profile> profiles = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profileDatabase = Room.databaseBuilder(this,
                ProfileDatabase.class,
                "profile_db")
                .allowMainThreadQueries()
                .build();

        displayAllProfiles();

        /*//recycler view
        recyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        profileAdapter = new ProfileAdapter(profiles, this);
        recyclerView.setAdapter(profileAdapter);*/



        // when sign up clicked
        signUpBtn = findViewById(R.id.signUpBtn);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivityForResult(i, 1);

            }
        });

        //when sign in clicked
        signInBtn = findViewById(R.id.signInBtn);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK){

            String login = data.getStringExtra("LOGIN");
            String email = data.getStringExtra("EMAIL");
            String password = data.getStringExtra("PASSWORD");

            createProfile(login, email, password);
        }
    }

    private void createProfile(String login, String email, String password) {
         long id = profileDatabase.getProfileDAO().addProfile(new Profile(login, password, email, 0));
         Profile profile = profileDatabase.getProfileDAO().getProfile(id);
         if(profile != null){
             profiles.add(0, profile);
             profileAdapter.notifyDataSetChanged();
         }

    }

    private void displayAllProfiles(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                profiles.addAll(profileDatabase.getProfileDAO().getAllProfiles());

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        profileAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }



}