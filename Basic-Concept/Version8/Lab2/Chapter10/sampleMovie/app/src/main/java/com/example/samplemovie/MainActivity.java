package com.example.samplemovie;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText ed1;
    Button btn1;
    RecyclerView rv;

    RequestQueue requestQueue;
    MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ed1 = findViewById(R.id.ed1);
        ed1.setText("http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=f5eef3421c602c6cb7ea224104795888&targetDt=20120101");

        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new GridLayoutManager(this, 1));
        adapter = new MovieAdapter();

        btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeReqeust(ed1.getText().toString());
            }
        });

        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
    }

    private void makeReqeust(String url) {
        if(!(url.startsWith("http://"))){
            url = "http://" + url;
        }

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("llll", response);
                processJSON(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("llll", error.toString());
            }
        });

        request.setShouldCache(false);
        requestQueue.add(request);
    }

    private void processJSON(String response) {
        Gson gson = new Gson();
        MovieList list = gson.fromJson(response, MovieList.class);
        int movieSize = list.boxOfficeResult.dailyBoxOfficeList.size();
        MovieAdapter adapter = new MovieAdapter();

        for(int i=0; i<movieSize; i++){
            Movie movie = list.boxOfficeResult.dailyBoxOfficeList.get(i);
            adapter.addItem(movie);
        }
        rv.setAdapter(adapter);
    }
}