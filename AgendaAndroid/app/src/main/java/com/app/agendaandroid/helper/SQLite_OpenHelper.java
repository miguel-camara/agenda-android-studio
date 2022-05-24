package com.app.agendaandroid.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.app.agendaandroid.util.Util;

public class SQLite_OpenHelper extends android.database.sqlite.SQLiteOpenHelper {

    public SQLite_OpenHelper( Context context ) {
        super( context, Util.DB_NAME, null, Util.DB_VERSION);
    }

    @Override
    public void onCreate( SQLiteDatabase db ) {
        db.execSQL( Util.CREATE_TABLE_USER );
        db.execSQL( Util.CREATE_TABLE_FAVORITE );
        db.execSQL( Util.CREATE_TABLE_EVENT );
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