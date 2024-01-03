package com.example.mission19;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {
    EditText ed1;
    TextView tv1;
    Button btn1;
    WebView webView;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ed1 = findViewById(R.id.ed1);
        ed1.setText("http://news.sbs.co.kr/news/SectionRssFeed.do?sectionId=01&plink=RSSREADER");

        tv1 = findViewById(R.id.tv1);

        webView = findViewById(R.id.webView);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String request) {
                view.loadUrl(request);
                return super.shouldOverrideUrlLoading(view, request);
            }
        });

        btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeRequest(ed1.getText().toString());
            }
        });

        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
    }

    private void makeRequest(String url) {
        if(!(url.startsWith("http://"))){
            url = "http://" + url;
        }
        String finalUrl = url;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                println("<응답받은 내용>\n" + response);
                webView.loadUrl(finalUrl);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                println("<에러 발생>\n" + error.toString());
            }
        });

        request.setShouldCache(false);
        requestQueue.add(request);
    }

    private void println(String str) {
        tv1.append(str + "\n\n");
    }
}