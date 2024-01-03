package com.example.sampleprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PersonProvider extends ContentProvider {
    private static final String AUTHORITY = "com.example.sampleprovider"; //패키지 이름
    private static final String BASE_PATH = "person"; //테이블 이름
    public static final Uri personUri = Uri.parse("content://"+AUTHORITY+"/"+BASE_PATH);

    private static final int PERSONS = 1;

    private static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    static{
        matcher.addURI(AUTHORITY, BASE_PATH, PERSONS);
    }

    SQLiteDatabase database;

    @Override
    public boolean onCreate() {
        DatabaseHelper helper = new DatabaseHelper(getContext());
        database = helper.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        Cursor cursor;
        switch (matcher.match(uri)){
            case PERSONS:
                cursor = database.query(DatabaseHelper.TBName, DatabaseHelper.allColumns, s, null, null, null, "name ASC");
                break;
            default:
                throw new IllegalArgumentException("알 수 없는 URI: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        long id = database.insert(DatabaseHelper.TBName, null, contentValues);

        if(id > 0){
            Uri _uri = ContentUris.withAppendedId(uri, id);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("삽입 실패 URI: " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int count = 0;

        switch (matcher.match(uri)){
            case PERSONS:
                count = database.delete(DatabaseHelper.TBName, s, strings);
            break;

            default:
                throw new IllegalArgumentException("알 수 없는 URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        int count = 0;

        switch (matcher.match(uri)){
            case PERSONS:
                count = database.update(DatabaseHelper.TBName, contentValues, s, strings);
                break;

            default:
                throw new IllegalArgumentException("알 수 없는 URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return count;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (matcher.match(uri)){
            case PERSONS:
                return "vnd.android.cursor.dir/persons"; // ContentProvider의 주소(URI)
            default:
                throw new IllegalArgumentException("알 수 없는 URI: " + uri);
        }
    }
}
