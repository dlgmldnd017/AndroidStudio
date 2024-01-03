package com.example.mission12;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText ed1;
    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ed1 = findViewById(R.id.ed1);
        tv1 = findViewById(R.id.tv1);

        Button btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyService.class);
                intent.putExtra("input", ed1.getText().toString());
                startService(intent);
            }
        });
        MyReceiver myReceiver = new MyReceiver();
        IntentFilter filter = new IntentFilter("com.example.Mission12.SHOW");
        this.registerReceiver(myReceiver, filter);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if(intent != null){
            tv1.setText(intent.getStringExtra("input"));
        }
        super.onNewIntent(intent);
    }
}