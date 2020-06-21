package com.codecorp.felipelima.jobschedulerservicetest;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import static com.codecorp.felipelima.jobschedulerservicetest.App.CHANNEL_ID;

public class ExempleJobService extends JobService {
    private static final String TAG = "Script";
    private boolean jobCancelled = false;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.i(TAG,"JobStarted");
        doBackgroundWork(jobParameters);

        return true;
    }

    private void doBackgroundWork(final JobParameters parameters){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++){
                    Log.i(TAG,"run:" + i);
                    if (jobCancelled){
                        return;
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Log.i(TAG,"Job finished");
                jobFinished(parameters,false);
            }
        }).start();
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.i(TAG,"Job Cancelled before completion");
        jobCancelled = true;
        return true;
    }
}
