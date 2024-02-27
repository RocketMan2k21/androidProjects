package com.example.luckynumber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity2 extends AppCompatActivity {

    TextView luckyNumber ;
    Button share_btn;

    TextView userView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        luckyNumber = findViewById(R.id.LuckyText);
        share_btn = findViewById(R.id.share_btn);
        userView = findViewById(R.id.username);

        Intent i = getIntent();
        String userName = i.getStringExtra("username");

        userView.setText(userName);
        int LuckyNumber = generateLuckyNumber();
        luckyNumber.setText(String.valueOf(LuckyNumber));

        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareData(userName, LuckyNumber);
            }
        });


    }


    public static int generateLuckyNumber(){
        Random random = new Random();
        return random.nextInt(1000);
    }

    public void ShareData(String username, int number){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");

        String num = String.valueOf(number);

        i.putExtra(Intent.EXTRA_SUBJECT, username+ " is lucky today!");
        i.putExtra(Intent.EXTRA_TEXT, num + " is lucky number!");

        startActivity(Intent.createChooser(i, "Choose a platform"));

    }
}