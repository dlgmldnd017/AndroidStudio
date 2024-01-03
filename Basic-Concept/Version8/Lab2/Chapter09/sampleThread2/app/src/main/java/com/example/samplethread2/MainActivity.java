package com.example.samplethread2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button btn1;
    TextView tv1;
    MyHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = findViewById(R.id.tv1);
        handler = new MyHandler();

        btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyThread myThread = new MyThread();
                myThread.start();
            }
        });
    }

    class MyThread extends Thread{
        @Override
        public void run() {
            for(int i=0; i<100; i++){
                try{
                    Thread.sleep(1000);
                }catch (Exception e){
                    e.printStackTrace();
                }

                Message message = handler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putInt("value", i+1);
                message.setData(bundle);
                handler.sendMessage(message);
            }
        }
    }

    class MyHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            int value = bundle.getInt("value");
            tv1.setText("value 값 : " + value);
        }
    }
}