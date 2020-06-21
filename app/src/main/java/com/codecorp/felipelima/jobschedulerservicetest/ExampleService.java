package com.codecorp.felipelima.jobschedulerservicetest;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.app.job.JobParameters;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import static com.codecorp.felipelima.jobschedulerservicetest.App.CHANNEL_ID;

public class ExampleService extends Service {
    private static final String TAG = "Script";
    private boolean jobCancelled = false;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("inputExtra");

        Log.i("Script","Extras: "+input);

        Intent notificationIntent = new Intent (this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent,0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Exemple service")
                .setContentText(input)
                .setSmallIcon(R.drawable.ic_android)
                .setContentIntent(pendingIntent)
                .setPriority(Notification.PRIORITY_MIN)//COLOCADO POR ULTIMO PARA FAZER UM TEST
                .build();

        startForeground(1, notification);
        doBackgroundWork();

        return START_STICKY;
    }

    private void doBackgroundWork(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000000; i++){
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
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
