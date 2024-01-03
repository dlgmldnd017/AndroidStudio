package com.example.sampledatabase2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView tv1;
    Button btn1, btn2, btn3;

    DatabaseHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = findViewById(R.id.tv1);

        btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDatabase();
            }
        });

        btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sqLiteDatabase == null) {
                    Toast.makeText(MainActivity.this, "데이터베이스를 먼저 만들어주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    insertInTable();
                }
            }
        });

        btn3 = findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                executeQuery();
            }
        });
    }

    private void createDatabase() {
        dbHelper = new DatabaseHelper(this);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        println("데이터베이스 <" + DatabaseHelper.DBName + "> 을 만들었습니다.");
    }

    private void insertInTable() {
        sqLiteDatabase.execSQL("insert into " + DatabaseHelper.TBName +
                "(name, age, number) " +
                " values " +
                " ('john', 12, '010-1234-1234')");
        println("<" + DatabaseHelper.TBName + "> 에 데이터 삽입 완료");
    }

    private void executeQuery(){
        StringBuilder builder = new StringBuilder();
        String selectSQL = "select _id, name, age, number from Music";
        Cursor cursor = sqLiteDatabase.rawQuery(selectSQL, null);
        int recordCount = cursor.getCount();

        for(int i=0; i<recordCount; i++){
            cursor.moveToNext();
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int age = cursor.getInt(2);
            String number = cursor.getString(3);
            builder.append("id: " + id + "\nname: " + name + "\nage: " + age + "\nnumber: " + number);
            builder.append("\n\n");
        }
        println("레코드 수: " + recordCount + "\n" + builder);
    }

    private void println(String str) {
        tv1.append(str + "\n\n");
    }
}