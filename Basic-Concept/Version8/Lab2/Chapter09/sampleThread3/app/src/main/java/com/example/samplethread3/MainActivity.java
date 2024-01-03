package com.example.samplethread3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Handler handler = new Handler();
    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = findViewById(R.id.tv1);

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
        int value;

        @Override
        public void run() {
            for(int i=0; i<100; i++){
                try{
                    Thread.sleep(1000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                value = i+1;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        tv1.setText("변경된 값: " + value);
                    }
                });
            }
        }
    }
}