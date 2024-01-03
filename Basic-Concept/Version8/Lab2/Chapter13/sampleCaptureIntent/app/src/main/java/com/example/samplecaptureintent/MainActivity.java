package com.example.samplecaptureintent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;

    File file;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });
    }

    public void checkPermission() {
        int cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        if (cameraPermission == PackageManager.PERMISSION_DENIED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.CAMERA}, 99);
            }
        }else{
            takePicture();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 99:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    takePicture();
                }else{
                    //권한이 허용되지 않은 경우
                }

                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void takePicture() {
        try {
            file = createFile();
            if (file.exists()) {
                file.delete();
            }

            //file.createNewFile();
        } catch(Exception e) {
            e.printStackTrace();
        }

        if(Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID, file);
            // 카메라앱과 공유하기 위해 uri를 만들어 파일제공자를 만든다.
        } else {
            uri = Uri.fromFile(file);
        }

        // 이때, uri는 "content://com.example.samplecaptureintent/files/capture.jpg" 값이 들어있다.

        Log.d("llll", uri.toString());
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

        startActivityForResult(intent, 101);
    }

    private File createFile() {
        String filename = "capture.jpg";
        File outFile = new File(getFilesDir(), filename);
        //getFilesDir()은 /data/user/0/com.example.samplecaptureintent/files 에 저장됨.

        return outFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == RESULT_OK) {
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
