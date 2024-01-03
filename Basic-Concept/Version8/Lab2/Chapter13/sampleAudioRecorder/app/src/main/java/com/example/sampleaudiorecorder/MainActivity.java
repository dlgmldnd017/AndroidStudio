package com.example.sampleaudiorecorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    MediaRecorder recorder;
    MediaPlayer player;

    File file;
    String filename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRecording();
            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRecording();
            }
        });

        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlay();
            }
        });

        Button button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlay();
            }
        });

        checkPermission();
    }

    private void checkPermission(){
        int record = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        int read = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int write = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if((record == PackageManager.PERMISSION_DENIED) ||
                (read == PackageManager.PERMISSION_DENIED) ||
                (write == PackageManager.PERMISSION_DENIED)){
            if(Build.VERSION.SDK_INT >= 24){
                requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 99);
            }
        }else{
            file = getOutputFile();
            if (file != null) {
                filename = file.getAbsolutePath();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 99:
                file = getOutputFile();
                if (file != null) {
                    filename = file.getAbsolutePath();
                }
                break;
        }
    }

    public File getOutputFile() {
        File mediaFile = null;
        try {
            File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), "MyApp");
            if (!mediaStorageDir.exists()){
                if (!mediaStorageDir.mkdirs()){
                    Log.d("MyCameraApp", "failed to create directory");
                    return null;
                }
            }

            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "recorded.mp4");
        } catch(Exception e) {
            e.printStackTrace();
        }

        return mediaFile;
    }

    public void startRecording() {
        if (recorder == null) {
            recorder = new MediaRecorder();
        }

        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        recorder.setOutputFile(filename);


        try {
            recorder.prepare();
            recorder.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopRecording() {
        if (recorder == null) {
            return;
        }

        recorder.stop();
        recorder.release();
        recorder = null;

        ContentValues values = new ContentValues(10);

        values.put(MediaStore.MediaColumns.TITLE, "Recorded");
        values.put(MediaStore.Audio.Media.ALBUM, "Audio Album");
        values.put(MediaStore.Audio.Media.ARTIST, "Mike");
        values.put(MediaStore.Audio.Media.DISPLAY_NAME, "Recorded Audio");
        values.put(MediaStore.Audio.Media.IS_RINGTONE, 1);
        values.put(MediaStore.Audio.Media.IS_MUSIC, 1);
        values.put(MediaStore.MediaColumns.DATE_ADDED,
                System.currentTimeMillis()/1000);
        values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp4");
        values.put(MediaStore.Audio.Media.DATA, filename);

        Uri audioUri = getContentResolver().insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, values);
        if (audioUri == null) {
            Log.d("SampleAudioRecorder", "Audio insert failed.");
            return;
        }
    }

    public void startPlay() {
        killMediaPlayer();

        try {
            player = new MediaPlayer();
            player.setDataSource(filename);
            player.prepare();
            player.start();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void stopPlay() {
        if (player != null) {
            player.stop();
        }
    }

    private void killMediaPlayer() {
        if (player != null) {
            try {
                player.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}