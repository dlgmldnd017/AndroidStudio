package com.example.samplekeypad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getCurrentFocus() != null){
                    InputMethodManager manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
            }
        });
    }
}