package com.bestdeveloper.workmanagerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Sending and receiving data using WorkManager
    public static final String KEY_COUNTER_VALUE = "key_count";

    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btn);



        // Constraints
        Constraints constraints = new Constraints.Builder()
                .setRequiresCharging(true)
                .build();

        // data creating

        Data data = new Data.Builder().putInt(KEY_COUNTER_VALUE, 200).build();


        WorkRequest countWorkRequest = new OneTimeWorkRequest.Builder(DemoWorker.class)
                //.setConstraints(constraints)
                .setInputData(data)
                .build();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // The exact time that the worker is going to be executed depends on the constraints that are used in your
                // WorkReuest and on system optimisations.
                // WorkManager is designed to give the best behavior under these restrictions.
                WorkManager.getInstance(getApplicationContext()).enqueue(countWorkRequest);
            }
        });

        //Let's display the Status of our worker
        WorkManager.getInstance(getApplicationContext()).getWorkInfoByIdLiveData(countWorkRequest.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if(workInfo != null){
                            Toast.makeText(getApplicationContext(), "Status " + workInfo.getState().name(),
                                    Toast.LENGTH_SHORT).show();
                        }

                        if(workInfo.getState().isFinished()){
                            Data data1 = workInfo.getOutputData();
                            String msg = data1.getString(DemoWorker.KEY_WORKER);
                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    }
                });




    }
}