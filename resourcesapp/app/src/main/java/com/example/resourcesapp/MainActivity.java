package com.example.resourcesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity {

    RadioButton radioButton;
    RadioGroup radioGroup;
    Button btn;

    Button dayPicker;

    Spinner spinner;

    TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radioButton = findViewById(R.id.radioButton);
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "With Delivery option selected", Toast.LENGTH_SHORT).show();
            }
        });
        //Radio Group
        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton1 = findViewById(i);
                Toast.makeText(MainActivity.this, "Selected: " + radioButton1.getText(), Toast.LENGTH_SHORT).show();


            }
        });



        //Spinner
        spinner = findViewById(R.id.spinner1);

        String[] courses = {"C++", "Java", "Kotlin", "Data Structures"};

        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item,
                courses);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        //action when something was selected
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, "Selected: " + courses[i], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(MainActivity.this, "Select course!", Toast.LENGTH_SHORT).show();
            }
        });

       btn = findViewById(R.id.check_time_btn);
       btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               DialogFragment timepickerFrag = new TimePickerFragment();
               timepickerFrag.show(getSupportFragmentManager(),
                       "Pick Time");
           }
       });

       dayPicker = findViewById(R.id.pick_date_btn);
       dayPicker.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               DialogFragment dayPickerFragment = new DatePickerFragment();
               dayPickerFragment.show(getSupportFragmentManager(),
                       "Pick Date");
           }
       });

       //ProgressBar
        ProgressBar progressBar = findViewById(R.id.progress_bar);
        progressBar.setProgress(50);

        //increase progress
        Button inc_btn = findViewById(R.id.increase_btn);
        inc_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.incrementProgressBy(10);
            }
        });


    }
}

