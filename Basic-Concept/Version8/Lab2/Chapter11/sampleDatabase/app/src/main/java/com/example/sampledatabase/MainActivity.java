package com.example.sampledatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText ed1, ed2;
    TextView tv1;
    Button btn1, btn2, btn3;

    SQLiteDatabase sqLiteDatabase;

    String databaseName, tableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ed1 = findViewById(R.id.ed1);
        ed2 = findViewById(R.id.ed2);
        tv1 = findViewById(R.id.tv1);

        btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseName = ed1.getText().toString();
                if(databaseName.equals("")){
                    Toast.makeText(MainActivity.this, "데이터베이스 이름을 입력하세요.", Toast.LENGTH_SHORT).show();
                }else{
                    createDatabase(databaseName);
                }
            }
        });

        btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tableName = ed2.getText().toString();
                if(tableName.equals("")){
                    Toast.makeText(MainActivity.this, "테이블 이름을 입력하세요.", Toast.LENGTH_SHORT).show();
                }else if(sqLiteDatabase == null){
                    Toast.makeText(MainActivity.this, "데이터베이스를 먼저 만들어주세요.", Toast.LENGTH_SHORT).show();
                }else{
                    createTable(tableName);
                }
            }
        });

        btn3 = findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sqLiteDatabase == null){
                    Toast.makeText(MainActivity.this, "데이터베이스를 먼저 만들어주세요.", Toast.LENGTH_SHORT).show();
                }else if(tableName.equals("")){
                    Toast.makeText(MainActivity.this, "테이블을 먼저 만들어주세요.", Toast.LENGTH_SHORT).show();
                }else{
                    insertInTable();
                }
            }
        });
    }

    private void createDatabase(String tableName) {
        sqLiteDatabase = this.openOrCreateDatabase(tableName, MODE_PRIVATE, null);
        println("데이터베이스 <" + tableName + "> 을 만들었습니다.");
    }

    private void createTable(String tableName) {
        sqLiteDatabase.execSQL("create table if not exists " + tableName + "("
                + "_id integer PRIMARY KEY autoincrement, "
                + "name text, "
                + "age integer, "
                + "number text)");
        println("<" + tableName + "> 데이터베이스 테이블 생성 완료" );
    }

    private void insertInTable(){
        sqLiteDatabase.execSQL("insert into " + tableName +
                "(name, age, number) " +
                " values " +
                " ('john', 12, '010-1234-1234')");
        println("<" + tableName + "> 에 데이터 삽입 완료");
    }

    private void println(String str){
        tv1.append(str+"\n\n");
    }
}