package com.exercise.samplecardview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class layout1 extends LinearLayout {
    ImageView imageView;
    TextView tv1, tv2;

    public layout1(Context context) {
        super(context);
        init(context);
    }

    public layout1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void setImageView(int resId){
        imageView.setImageResource(resId);
    }

    public void setName(String name){
        tv1.setText(name);
    }

    public void setMobile(String mobile){
        tv2.setText(mobile);
    }

    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout1, this, true);

        imageView = findViewById(R.id.imageView);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
    }
}