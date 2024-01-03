package com.example.sampleprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button btn1, btn2, btn3, btn4, btn5;
    TextView tv1;

    DatabaseHelper database = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = findViewById(R.id.tv1);

        btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queryTable();
                println("\n\n");
            }
        });

        btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertInTable();
                println("\n\n");
            }
        });

        btn3 = findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn4 = findViewById(R.id.btn4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn5 = findViewById(R.id.btn5);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv1.setText("");
            }
        });
    }

    private void queryTable(){
        println("데이터 조회 시작");
        String uriStr = "content://com.example.sampleprovider/person";
        Uri uri = Uri.parse(uriStr);

        Cursor cursor = getContentResolver().query(uri, DatabaseHelper.allColumns, null, null, "_id ASC");
        for(int i=0; cursor.moveToNext(); i++){
            String str = null;
            int _id = cursor.getInt(0);
            String name = cursor.getString(1);
            int age = cursor.getInt(2);
            String number = cursor.getString(3);
            str = "_id: " + _id + "\n" + "name: " + name + "\n" + "age: " + age + "\n" + "number" + number;
            println(str);
        }
    }

    private void insertInTable() {
        println("데이터 삽입 시작");
        String uriStr = "content://com.example.sampleprovider/person";
        Uri uri = Uri.parse(uriStr);

        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        String[] columns = cursor.getColumnNames();
        for(int i=0; i<columns.length; i++){
            println("#"+i+": " + columns[i]);
        }

        ContentValues values = new ContentValues();
        values.put("name", "lee");
        values.put("age", 20);
        values.put("number", "010-1234-1234");

        uri = getContentResolver().insert(uri, values);
        println("삽입 결과: " + uri.toString());
    }

    private void updateTable(){
        Uri uri = Uri.parse("com.example.sampleprovider/person");
    }

    private void deleteTable(){

    }

    private void println(String str){
        tv1.append(str + "\n\n");
    }
}