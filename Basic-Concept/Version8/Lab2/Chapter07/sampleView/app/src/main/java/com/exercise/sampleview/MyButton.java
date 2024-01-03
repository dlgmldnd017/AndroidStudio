package com.exercise.sampleview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

public class MyButton extends AppCompatButton {
    public MyButton(@NonNull Context context) {
        super(context);
        init(context);
    }

    public MyButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        setBackgroundColor(Color.CYAN);
        setTextColor(Color.BLACK);

        float textSize = getResources().getDimension(R.dimen.text_size);
        setTextSize(textSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("TAG1", "onDraw() 호출");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("TAG1", "onTouchEvent() 호출");

        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                setBackgroundColor(Color.BLUE);
                setTextColor(Color.RED);
                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
            case MotionEvent.ACTION_UP:
                setBackgroundColor(Color.CYAN);
                setTextColor(Color.BLACK);
                break;
        }
        invalidate();

        return true;
    }
}
