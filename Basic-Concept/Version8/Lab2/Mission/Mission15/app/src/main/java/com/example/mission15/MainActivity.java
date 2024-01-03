package com.example.mission15;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    Button btn1,btn2;
    LinearLayout slidingPageLayout;

    Animation goLeft, goRight;

    boolean isOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        slidingPageLayout = findViewById(R.id.SlidingPageLayout);

        goLeft = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_go_left);
        goRight = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_go_right);

        SlidingPageListener listener = new SlidingPageListener();
        goLeft.setAnimationListener(listener);
        goRight.setAnimationListener(listener);

        btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingPageLayout.setVisibility(View.VISIBLE);
                slidingPageLayout.startAnimation(goLeft);

            }
        });

        btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingPageLayout.startAnimation(goRight);
            }
        });
    }

    class SlidingPageListener implements Animation.AnimationListener{

        @Override
        public void onAnimationStart(Animation animation) {
            Log.d("llll", "onAnimationStart()");
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            Log.d("llll", "onAnimationEnd()");
            if(isOpen){
                slidingPageLayout.setVisibility(View.INVISIBLE);
                isOpen=false;
            }else{
                isOpen=true;
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}