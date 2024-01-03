package com.exercise.samplerecyclerview2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.exercise.samplerecyclerview.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.rv1);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        PersonAdapter adapter = new PersonAdapter();

        adapter.addItem(new Person("1", "010-1000-1000"));
        adapter.addItem(new Person("2", "010-2000-2000"));
        adapter.addItem(new Person("3", "010-3000-3000"));
        adapter.addItem(new Person("4", "010-4000-3000"));
        adapter.addItem(new Person("5", "010-5000-3000"));
        adapter.addItem(new Person("6", "010-6000-3000"));
        adapter.addItem(new Person("7", "010-7000-3000"));
        adapter.addItem(new Person("8", "010-8000-3000"));
        adapter.addItem(new Person("9", "010-9000-3000"));
        adapter.addItem(new Person("10", "010-1000-3000"));
        adapter.addItem(new Person("11", "010-1100-3000"));
        adapter.addItem(new Person("12", "010-1200-3000"));

        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnPersonItemClickListener() {
            @Override
            public void onItemClick(PersonAdapter.ViewHolder holder, View view, int position) {
                Log.d("TAG1","main의 onItemClick 실행");
                Person item = adapter.getItem(position);
                Toast.makeText(MainActivity.this, "아이템 선택됨." + item.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}