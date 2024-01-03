package com.example.sampleprovider;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DBName = "person.db";
    public static String TBName = "person";
    public static String[] allColumns = {"_id", "name", "age", "number"};
    public static int version = 1;

    public DatabaseHelper(Context context){
        super(context, DBName, null, version);
    }

    @Override
    public void onCreate(android.database.sqlite.SQLiteDatabase sqLiteDatabase) {
        String sql = "create table if not exists " + TBName + " (" +
                "_id integer PRIMARY KEY autoincrement, " +
                "name text, " +
                "age integer," +
                "number text)";

        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TBName);
        onCreate(sqLiteDatabase);
    }
}
