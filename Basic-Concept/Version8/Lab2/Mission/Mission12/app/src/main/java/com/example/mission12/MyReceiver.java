package com.example.mission12;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("llll", "onReceive() 호출");
        sendToActivity(context, intent.getStringExtra("input"));
    }

    private void sendToActivity(Context context, String input){
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|
                Intent.FLAG_ACTIVITY_SINGLE_TOP|
                Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("input", input);
        context.startActivity(intent);
    }
}