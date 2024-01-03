package com.example.mission25;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    RecyclerView recyclerView;
    PictureAdapter adapter;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    int pictureCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new PictureAdapter();

        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnPictureItemClickListener() {
            @Override
            public void onItemClick(PictureAdapter.ViewHolder holder, View view, int position) {
                Log.d("llll", "Main의 setOntiemClickListener()");
                PictureInfo item = adapter.getItem(position);
                Toast.makeText(getApplicationContext(), "아이템 선택됨 : " + item.getDisplayName(), Toast.LENGTH_LONG).show();
            }
        });

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<PictureInfo> result = queryAllPictures();

                adapter.setItems(result);
                adapter.notifyDataSetChanged();
            }
        });
        checkPermission();

    }

    private void checkPermission(){
        int read = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int write = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if((read == PackageManager.PERMISSION_DENIED) ||
                (write == PackageManager.PERMISSION_DENIED)){
            if(Build.VERSION.SDK_INT >= 24){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 99);
            }
        }else{
            ArrayList<PictureInfo> result = queryAllPictures();
            adapter.setItems(result);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case 99:
                ArrayList<PictureInfo> result = queryAllPictures();
                adapter.setItems(result);
                adapter.notifyDataSetChanged();
                break;
        }
    }

    private ArrayList<PictureInfo> queryAllPictures() {
        ArrayList<PictureInfo> result = new ArrayList<>();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        // uri에는 "content://media/external/images/media" 값이 들어있음.

        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.DISPLAY_NAME, MediaStore.MediaColumns.DATE_ADDED};

        Cursor cursor = getContentResolver().query(uri, projection, null, null, MediaStore.MediaColumns.DATE_ADDED +
                " asc");
        int columnDataIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        int columnNameIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME);
        int columnDateIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_ADDED);

        pictureCount = 0;
        while (cursor.moveToNext()) {
            String path = cursor.getString(columnDataIndex);
            String displayName = cursor.getString(columnNameIndex);
            String outDate = cursor.getString(columnDateIndex);
            String addedDate = dateFormat.format(new Date(new Long(outDate).longValue() * 1000L));

            if (!TextUtils.isEmpty(path)) {
                PictureInfo info = new PictureInfo(path, displayName, addedDate);
                result.add(info);
            }
            pictureCount++;
        }

        textView.setText(pictureCount + " 개");
        return result;
    }
}