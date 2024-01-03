package com.example.samplepush;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        Log.d("llll", task.getResult());
                    }
                });

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               try{
                   String instanceId =  FirebaseAuth.getInstance().getCurrentUser().getUid();
                   println("확인된 인스턴스 id " + instanceId);
               }catch (Exception e){
                   e.printStackTrace();
               }
            }
        });
    }

    public void println(String data) {
        textView2.append(data + "\n");
        Log.d("FMS", data);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        println("onNewIntent 호출됨");
        if (intent != null) {
            processIntent(intent);
        }

        super.onNewIntent(intent);
    }

    private void processIntent(Intent intent) {
        String from = intent.getStringExtra("from");
        if (from == null) {
            println("from is null.");
            return;
        }

        String contents = intent.getStringExtra("contents");
        println("DATA : " + from + ", " + contents);
        textView.setText("[" + from + "]로부터 수신한 데이터 : " + contents);
    }
}