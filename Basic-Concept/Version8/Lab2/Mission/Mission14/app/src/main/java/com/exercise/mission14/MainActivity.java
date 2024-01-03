package com.exercise.mission14;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv1);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        PersonAdapter adapter = new PersonAdapter();

        adapter.addItem(new Person("패딩", 100+",000", "특가 상품", R.drawable.clothes1));
        adapter.addItem(new Person("살구잠바", 300+",000", "기획 상품", R.drawable.clothes2));
        adapter.addItem(new Person("남색 코트", 500+",000", "기획 상품", R.drawable.clothes3));
        adapter.addItem(new Person("초록색 파카", 30+",000", "떨이 상품", R.drawable.clothes4));
        adapter.addItem(new Person("블랙 코트", 200+",000", "특가 상품", R.drawable.clothes5));
        adapter.addItem(new Person("반팔 패딩", 10+",000", "떨이 상품", R.drawable.clothes6));
        adapter.addItem(new Person("검은색 코트", 100+",000", "오늘만 상품", R.drawable.clothes7));
        adapter.addItem(new Person("청록색 코트", 100+",000", "오늘만 상품", R.drawable.clothes8));

        recyclerView.setAdapter(adapter);

        adapter.setOnClickItem(new OnPersonItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Person selectedItem = adapter.getItem(position);
                String itemName = selectedItem.getName();
                Toast.makeText(MainActivity.this, itemName, Toast.LENGTH_SHORT).show();
            }
        });
    }
}