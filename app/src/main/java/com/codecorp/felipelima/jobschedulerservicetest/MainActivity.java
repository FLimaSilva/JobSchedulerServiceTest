package com.codecorp.felipelima.jobschedulerservicetest;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Script";
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        JobScheduler jobScheduler = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
        boolean hasBeenScheduled = false;

        for (JobInfo ji:jobScheduler.getAllPendingJobs()) {
            if (ji.getId() == 123){
                hasBeenScheduled = true;
                break;
            }
        }

        Log.i(TAG,"PendingJobs"+hasBeenScheduled);

        editText =  findViewById(R.id.editText);
    }

    public void scheduleJob (View view){
        ComponentName cp = new ComponentName(this,ExempleJobService.class);
        JobInfo info = new JobInfo.Builder(123,cp)
                //.setRequiresCharging(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true)
                //.setPeriodic(1000)
                .setMinimumLatency(1000)
                .build();
        JobScheduler scheduler = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);

        if (resultCode == JobScheduler.RESULT_SUCCESS){
            Log.i(TAG,"Job scheduled");
        } else {
            Log.i(TAG,"Job scheduling failed");
        }

    }

    public void cancelJob (View view){
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(123);
        Log.i(TAG,"Job cancelled");
    }

    @TargetApi(Build.VERSION_CODES.O)
    public void startService (View view){
        String input = editText.getText().toString();

        Intent intent = new Intent(this,ExampleService.class);
        intent.putExtra("inputExtra", input);

        startForegroundService(intent);
    }

    public void stopService (View view){
        Intent intent = new Intent(this,ExampleService.class);
        stopService(intent);
    }
}
