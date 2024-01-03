package com.example.mission12;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.example.mission12.MainActivity;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent == null){
            return START_STICKY;
        }else{
            Intent myIntent = new Intent();
            myIntent.setAction("com.example.Mission12.SHOW");
            myIntent.putExtra("input", intent.getStringExtra("input"));
            sendBroadcast(myIntent);
        }
        return super.onStartCommand(intent, flags, startId);
    }
}