package com.example.mission26;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    FrameLayout previewFrame;
    CameraSurfaceView cameraView;

    RelativeLayout iconLayout;
    ImageView icon01Image;
    ImageView icon02Image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        previewFrame = findViewById(R.id.previewFrame);
        cameraView = findViewById(R.id.cameraView);

        iconLayout = findViewById(R.id.iconLayout);
        icon01Image = findViewById(R.id.icon01Image);
        icon02Image = findViewById(R.id.icon02Image);


        Button showButton = findViewById(R.id.showButton);
        showButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                iconLayout.setVisibility(View.VISIBLE);
            }
        });

        Button hideButton = findViewById(R.id.hideButton);
        hideButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                iconLayout.setVisibility(View.INVISIBLE);
            }
        });

        checkPermission();
    }

    private void checkPermission(){
        int camera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int loc = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if((camera == PackageManager.PERMISSION_DENIED) ||
                (loc == PackageManager.PERMISSION_DENIED)){
            if(Build.VERSION.SDK_INT >= 24){
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION},
                        99);
            }
        }else{

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 99:
                break;
        }
    }
}
