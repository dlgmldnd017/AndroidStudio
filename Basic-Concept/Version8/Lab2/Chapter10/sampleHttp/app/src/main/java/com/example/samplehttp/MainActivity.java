package com.example.samplehttp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    EditText ed1;
    TextView tv1;
    Button btn1;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ed1 = findViewById(R.id.ed1);
        tv1 = findViewById(R.id.tv1);

        btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlStr = ed1.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        request(urlStr);
                    }
                }).start();
            }
        });
    }

    private void request(String urlStr) {
        StringBuilder output = new StringBuilder();
        if(!(urlStr.startsWith("http://"))){
            urlStr = "http://" + urlStr;
        }
        try {
            URL url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            if(con != null){
                con.setRequestMethod("GET");
                con.setConnectTimeout(10000);
                con.setDoInput(true);

                int responseCode = con.getResponseCode();
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                String line = null;
                while(true){
                    line = reader.readLine();
                    if(line == null){
                        break;
                    }
                    output.append(line);
                }
                reader.close();
                con.disconnect();
            }
        } catch (Exception e) {
            println(e.toString());
        }
        println("<응답>\n" + output);
    }

    private void println(String str){
        handler.post(new Runnable() {
            @Override
            public void run() {
                tv1.append(str+"\n");
            }
        });
    }
}