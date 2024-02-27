package com.example.counterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView counterText;
    Button pluscount;
    int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        counterText = findViewById(R.id.counter);
        pluscount = findViewById(R.id.button);

        pluscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counterText.setText("" + increaseCount());
            }
        });

    }

    public int increaseCount(){
        return ++counter;
    }
}