package com.example.samplecapture;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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

import java.util.List;

public class MainActivity extends AppCompatActivity {
    CameraSurfaceView cameraView;
    FrameLayout previewFrame;

    boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        previewFrame = findViewById(R.id.previewFrame);
        cameraView = new CameraSurfaceView(this);

        checkPermission();

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkPermission();
            }
        });
    }

    private void checkPermission() {
        int camera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int read = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int write = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        String[] requestPermission = {android.Manifest.permission.CAMERA,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};


        if ((camera == PackageManager.PERMISSION_DENIED) ||
                (read == PackageManager.PERMISSION_DENIED) ||
                (write == PackageManager.PERMISSION_DENIED)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{requestPermission[0], requestPermission[1], requestPermission[2]}, 99);
            }
        }else{
            // 권한이 허용된 상태에서 프리뷰 해줌.
            if(isFirst){
                previewFrame.addView(cameraView);
            }
            takePicture();
            isFirst = false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 99:
                if (grantResults.length > 2 && (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED) &&
                        grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                    previewFrame.addView(cameraView);
                }
            break;
        }
    }

    public void takePicture() {
        cameraView.capture(new Camera.PictureCallback() {
            public void onPictureTaken(byte[] data, Camera camera) {
                try {
                   Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                   String outUri = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "111", "hello~");

                  if(outUri == null){
                      return ;
                  }else{
                      Uri uri = Uri.parse(outUri);
                      sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                  }
                  
                   camera.startPreview();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
        private SurfaceHolder mHolder;
        private Camera camera = null;

        public CameraSurfaceView(Context context) {
            super(context);

            mHolder = getHolder();
            mHolder.addCallback(this);
        }

        public void surfaceCreated(SurfaceHolder holder) {
            getCameraInstance();
            setCameraOrientation();
            // 회전에 따른 orientation이 각기 다르기 때문에
            // 상황에 맞게 구현

            try {
                camera.setPreviewDisplay(mHolder);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            try {
                // 화면이 회전할 때 또는 화면의 크기가 변경될 때마다 초기화 됨.
                camera.startPreview();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
            try {
                camera.stopPreview();
                camera.release();
                camera = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public boolean capture(Camera.PictureCallback handler) {
            if (camera != null) {
                camera.takePicture(null, null, handler);
                return true;
            } else {
                return false;
            }
        }

        public void getCameraInstance() {
            try {
                camera = Camera.open();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void setCameraOrientation() {
            if (camera == null) {
                return;
            }

            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(0, info);
            // info객체에게 카메라에 대한 특정 정보를 전달함.

            WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            int rotation = manager.getDefaultDisplay().getRotation();
            Log.d("llll", rotation+"");
            // 0을 반환함.

            int degrees = 0;
            switch (rotation) {
                case Surface.ROTATION_0:
                    degrees = 0;
                    break;
                case Surface.ROTATION_90:
                    degrees = 90;
                    break;
                case Surface.ROTATION_180:
                    degrees = 180;
                    break;
                case Surface.ROTATION_270:
                    degrees = 270;
                    break;
            }

            int result;
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                result = (info.orientation + degrees) % 360;
                result = (360 - result) % 360;
            } else {
                result = (info.orientation - degrees + 360) % 360;
            }
            camera.setDisplayOrientation(result);
        }

    }

}