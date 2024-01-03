package com.example.samplecustomviewstyle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class CustomViewStyle extends View {
    Paint paint;

    public CustomViewStyle(Context context) {
        super(context);
        init(context);
    }

    public CustomViewStyle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        try{
            canvas.clipRect(220, 240, 250, 270);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.RED);
            canvas.drawRect(220, 240, 320, 340, paint);
        }catch (Exception e){
            Log.d("llll", e.getMessage());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            Toast.makeText(super.getContext(), "좌표: " + event.getX() +", " + event.getY(), Toast.LENGTH_SHORT).show();
        }

        return super.onTouchEvent(event);
    }
}
