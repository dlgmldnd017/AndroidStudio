package com.example.sampledelayed;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button btn1;
    TextView tv1;

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = findViewById(R.id.tv1);

        btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    request();
                }catch (Exception e){

                }
            }
        });

        Button btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv1.setText("핸들러가 실행되는 중에도 바꿀 수가 있어요.");
            }
        });

        handler = new Handler();
    }

    private void request() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("원격 요청");
        builder.setMessage("데이터를 요청하시겠습니까?");

        builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                tv1.setText("3초후에 표시됩니다...");

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tv1.setText("요청 완료됨.");
                    }
                }, 3000);
            }
        });

        builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}