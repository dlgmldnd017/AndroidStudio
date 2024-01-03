package com.example.sampledatabase2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

class DatabaseHelper extends SQLiteOpenHelper {
    public static String DBName = "Sound.db";
    public static String TBName = "Music";
    public static int version = 1;

    public DatabaseHelper(Context context) {
        super(context, DBName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        println("onCreate() 호출");
        db.execSQL("create table if not exists " + TBName + "("
                + "_id integer PRIMARY KEY autoincrement, "
                + "name text, "
                + "age integer, "
                + "number text)");
        println("<" + TBName + "> 데이터베이스 테이블 생성 완료");
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        println("onOpen() 호출");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        println("onUpgrade() 호출: " + oldVersion + " -> " + newVersion);
    }

    public void println(String str){
        Log.d("llll", str + "\n\n");
    }
}