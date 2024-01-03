package com.example.mission17;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class CustomerInfo extends LinearLayout {
    TextView tv1,tv2,tv3;
    ImageView img1;

    public CustomerInfo(Context context) {
        super(context);
        init(context);
    }

    public CustomerInfo(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.customer_info, this, true);

        tv1 = findViewById(R.id.cus_tv1);
        tv2 = findViewById(R.id.cus_tv2);
        tv3 = findViewById(R.id.cus_tv3);
        img1 = findViewById(R.id.cus_img1);
    }

    public void setName(String name){
        tv1.setText(name);
    }
    public void setNumber(String number){
        tv2.setText(number);
    }
    public void setArea(String area){
        tv3.setText(area);
    }
    public void setImage(int drawable){
        img1.setImageResource(drawable);
    }
}
