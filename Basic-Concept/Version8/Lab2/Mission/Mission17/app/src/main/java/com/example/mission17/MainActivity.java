package com.example.mission17;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView main_tv1;

    Animation goIn, goOut, goRest;

    Handler handler;

    FrameLayout frameLayout;
    CustomerInfo[] cus = new CustomerInfo[3];

    int[] drawables = {R.drawable.face1, R.drawable.face2, R.drawable.face3};
    String[] names = {"홍길동", "손흥민", "박지성"};
    String[] numbers = {"010 - 1111 - 1234", "010 - 2222 - 4567", "010 - 3333 - 8901"};
    String[] areas = {"서울시", "수원시", "구미시"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_tv1 = findViewById(R.id.main_tv2);

        frameLayout = findViewById(R.id.container);
        for(int i=0; i<cus.length; i++){
            cus[i] = new CustomerInfo(this);
            setCustomerInfo(cus[i], drawables[i], names[i], numbers[i], areas[i]);
            frameLayout.addView(cus[i]);
            cus[i].setVisibility(View.INVISIBLE);
        }

        goIn = AnimationUtils.loadAnimation(this, R.anim.go_in);
        goOut = AnimationUtils.loadAnimation(this, R.anim.go_out);
        goRest = AnimationUtils.loadAnimation(this, R.anim.go_rest);

        handler = new Handler();
        MyThread thread = new MyThread();
        thread.start();
    }
    class MyThread extends Thread{
        int i=0;
        public void run() {
            //init setting
            cus[0].startAnimation(goIn);
            cus[0].setVisibility(View.VISIBLE);
            callSleep();

            // infinite circle
            while(true){
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(i==0){
                            main_tv1.setText("2/3");
                            cus[0].startAnimation(goOut);
                            cus[1].startAnimation(goIn);
                            cus[2].startAnimation(goRest);

                            cus[1].setVisibility(View.VISIBLE);
                            i++;
                        }else if(i==1){
                            main_tv1.setText("3/3");
                            cus[0].startAnimation(goRest);
                            cus[1].startAnimation(goOut);
                            cus[2].startAnimation(goIn);

                            cus[1].setVisibility(View.INVISIBLE);
                            cus[2].setVisibility(View.VISIBLE);
                            i++;
                        }else if(i==2) {
                            main_tv1.setText("1/3");
                            cus[0].startAnimation(goIn);
                            cus[1].startAnimation(goRest);
                            cus[2].startAnimation(goOut);

                            cus[2].setVisibility(View.INVISIBLE);
                            cus[0].setVisibility(View.VISIBLE);
                            i = 0;
                        }
                    }
                });
                callSleep();
            }
        }
        public void callSleep(){
            try{
                Thread.sleep(5000);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void setCustomerInfo(CustomerInfo customerInfo, int drawable, String name, String number, String area){
        customerInfo.setImage(drawable);
        customerInfo.setName(name);
        customerInfo.setNumber(number);
        customerInfo.setArea(area);
    }
}