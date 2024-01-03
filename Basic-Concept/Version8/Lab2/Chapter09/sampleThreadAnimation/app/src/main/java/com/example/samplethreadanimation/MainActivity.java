package com.example.samplethreadanimation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btn1;
    ImageView img1;

    ArrayList<Drawable> arr = new ArrayList<>();
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img1 = findViewById(R.id.img1);
        Resources res = getResources();
        arr.add(res.getDrawable(R.drawable.face1));
        arr.add(res.getDrawable(R.drawable.face2));
        arr.add(res.getDrawable(R.drawable.face3));
        arr.add(res.getDrawable(R.drawable.face4));
        arr.add(res.getDrawable(R.drawable.face5));

        btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyThread thread = new MyThread();
                thread.start();
            }
        });

        handler = new Handler();
    }

    class MyThread extends Thread{
        @Override
        public void run() {
            int index = 0;
            for(int i=0; i<50; i++){
                if(index>4){
                    index=0;
                }
                Drawable drawable = arr.get(index++);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        img1.setImageDrawable(drawable);
                    }
                });

                try{
                    Thread.sleep(800);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}