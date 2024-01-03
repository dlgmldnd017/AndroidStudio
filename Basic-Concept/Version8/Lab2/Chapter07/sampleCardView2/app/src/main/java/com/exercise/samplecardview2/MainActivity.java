package com.exercise.samplecardview2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout1 layout1 = findViewById(R.id.layout1);
        layout1.setImageView(R.drawable.ic_launcher_background);
        layout1.setName("김민수");
        layout1.setMobile("010-1234-1234");

        Button btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout1.setImageView(R.drawable.profile1);
            }
        });

        Button btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout1.setImageView(R.drawable.profile2);
            }
        });
    }
}