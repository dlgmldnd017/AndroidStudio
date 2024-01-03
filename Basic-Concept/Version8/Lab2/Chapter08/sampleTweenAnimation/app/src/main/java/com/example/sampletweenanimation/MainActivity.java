package com.example.sampletweenanimation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale);

        Button btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animation1);
            }
        });
        //btn1.setAnimation(animation);

        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate);

        Button btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animation2);
            }
        });

        Animation animation3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);

        Button btn3 = findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animation3);
            }
        });

        Animation animation4 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);

        Button btn4 = findViewById(R.id.btn4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animation4);
            }
        });
    }
}