package com.example.samplethread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyThread thread = new MyThread();
                thread.start();
            }
        });
    }
    class MyThread extends Thread{
        @Override
        public void run() {
            for(int i=0; i<100; i++){
                Log.d("llll", i+1 + "");
                try{
                    Thread.sleep(1000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                tv1.setText(i+1 + "");
            }
        }
    }
}