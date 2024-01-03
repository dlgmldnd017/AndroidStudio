package com.example.samplevibrate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 = findViewById(R.id.button);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                if(Build.VERSION.SDK_INT >=26){
                    vibrator.vibrate(VibrationEffect.createOneShot(1000, 1));
                }else{
                    vibrator.vibrate(1000);
                }
            }
        });

        Button btn2 = findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Toast.makeText(MainActivity.this, uri.toString(), Toast.LENGTH_SHORT).show();
                Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), uri);
                ringtone.play();
            }
        });

        Button btn3 = findViewById(R.id.button3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer player = MediaPlayer.create(getApplicationContext(), R.raw.beep);
                player.start();
            }
        });
    }
}