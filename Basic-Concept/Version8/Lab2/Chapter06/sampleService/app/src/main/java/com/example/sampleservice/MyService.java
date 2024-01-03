package com.example.sampleservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

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
        Log.d("llll", "onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("llll", "onStartCommand()");

        if(intent == null){
            return Service.START_STICKY;
        }else{
            String command = intent.getStringExtra("command");
            String key = intent.getStringExtra("key");

            Log.d("llll", "command: " + command + ", key: " + key);

            for(int i=0; i<5; i++){
                try{
                    Thread.sleep(1000);
                }catch (Exception e){}
                Log.d("llll", "Waiting " + i + "seconds.");
            }
        }
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|
                Intent.FLAG_ACTIVITY_CLEAR_TOP|
                Intent.FLAG_ACTIVITY_NEW_TASK);
        myIntent.putExtra("key", "hello");
        startActivity(myIntent);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("llll", "onDestroy()");
    }
}