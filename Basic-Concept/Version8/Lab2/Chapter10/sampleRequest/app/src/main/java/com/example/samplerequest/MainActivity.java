package com.example.samplerequest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.URL;

public class MainActivity extends AppCompatActivity {
    EditText ed1;
    TextView tv1;
    Button btn1;

    RequestQueue requestQueue;

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
                makeRequest(ed1.getText().toString());
            }
        });

        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(this);
        }
    }

    private void makeRequest(String url) {
        if(!(url.startsWith("http://"))){
            url = "http://" + url;
        }
        StringRequest request = new StringRequest(0, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                println("<응답 내용>\n\n" + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                println("<에러 내용>\n\n" + error.toString());
            }
        });

        request.setShouldCache(false);
        requestQueue.add(request);
        println("요청을 보냈음.");
    }

    private void println(String str){
        tv1.append(str+"\n\n");
    }
}