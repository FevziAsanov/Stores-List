package com.example.fevzi.storeslist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by fevzi on 30.09.14.
 */
public class SQLHelper extends SQLiteOpenHelper {

    final String LOG_TAG = "myLogs";

    public SQLHelper(Context context) {
        super(context, "MyDB", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "--- onCreate database ---");
        db.execSQL("create table Product( id integer primary key, lng integer, " +
                "lat integer, " +
                "title text , " +
                "description text, " +
                "avatar text, " +
                "author_ID integer  );");

        db.execSQL("create table Author( id integer primary key autoincrement," +
                 " name text," +
                 " email text," +
                 " token text );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
