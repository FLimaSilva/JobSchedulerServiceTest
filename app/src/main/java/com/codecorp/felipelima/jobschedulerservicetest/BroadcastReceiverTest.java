package com.codecorp.felipelima.jobschedulerservicetest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class BroadcastReceiverTest extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent it = new Intent(context,ExampleService.class);
        it.putExtra("inputExtra", "");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(it);
        }
    }
}
