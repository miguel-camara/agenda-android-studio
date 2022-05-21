package com.app.cita_sqlite.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class SQLite_OpenHelper extends android.database.sqlite.SQLiteOpenHelper {
    public static final String TABLE_NAME = "quotes";
    private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_NAME + " (id INTEGER PRIMARY KEY, name TEXT, surname TEXT, phone TEXT, category TEXT, date TEXT, hour TEXT)";
    public static final String DB_NAME = "DemoQuote";
    public static final int DB_VERSION = 1;

    public SQLite_OpenHelper( Context context ) {
        super( context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

    // Open DB
    public void openConn() {
        this.getReadableDatabase();
    }

    // Close DB
    public void closeConn() {
        this.close();
    }
}
