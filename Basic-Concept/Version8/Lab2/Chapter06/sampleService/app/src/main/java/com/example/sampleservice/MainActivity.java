package com.example.sampleservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText ed1;
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ed1 = findViewById(R.id.ed1);

        btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ed1.getText().toString();

                Intent intent = new Intent(getApplicationContext(), MyService.class);
                intent.putExtra("command", "show");
                intent.putExtra("key",name);
                startService(intent);
            }
        });

        Intent myIntent = getIntent();
        if(myIntent != null){
            String key = myIntent.getStringExtra("key");
            if(key != null){
                Log.d("llll", "key from Service is " + key);
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if(intent != null){
            String key = intent.getStringExtra("key");
            Log.d("llll", "key from Service is " + key);
        }
        super.onNewIntent(intent);
    }
}