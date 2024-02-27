package com.example.sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView textView;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);
        button = findViewById(R.id.clickBtn);

        //check shared prefence for data in it
        DisplaySavedText();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredText = editText.getText().toString();
                DisplayAndSaveText(enteredText);
            }
        });
    }

    private void DisplaySavedText() {
        // retreiving the value from shared pref
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String s1 = sharedPreferences.getString("name", " ");

        textView.setText(s1);
    }

    private void DisplayAndSaveText(String text) {
        //Display the text
        textView.setText(text);

        //Saving the Text into SharedPref
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        //Writing data to shared pref
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", text);
        editor.commit();
    }
}