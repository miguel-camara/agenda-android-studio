package com.app.agendaandroid.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.app.agendaandroid.helper.SQLite_OpenHelper;
import com.app.agendaandroid.model.User;
import com.app.agendaandroid.util.Util;

import java.util.ArrayList;
import java.util.List;

public class UserController {
    SQLite_OpenHelper dbHlpr;
    SQLiteDatabase db;

    ContentValues values;

    List<User> userList;

    public UserController(Context context) {
        dbHlpr = new SQLite_OpenHelper( context );
    }

    public long createQuote( User user) {
        db = dbHlpr.getWritableDatabase();

        values = new ContentValues();
        values.put( "name", user.getName() );
        values.put( "surname", user.getSurname() );
        values.put( "phone", user.getPhone() );
        values.put( "category", user.getCategory() );
        values.put( "date", user.getDate() );
        values.put( "hour", user.getHour() );

        return db.insert( Util.TABLE_USER, null, values);
    }

    public List<User> readQuote() {
        userList = new ArrayList<>();

        db = dbHlpr.getReadableDatabase();

        Cursor cursor = db.query( Util.TABLE_USER, null, null, null, null, null, null);

        if ( cursor.moveToFirst() ) {
            do {
                User user = new User( cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6) );
                userList.add(user);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return userList;
    }

    public long updateQuote( User user) {
        db = dbHlpr.getWritableDatabase();

        values = new ContentValues();

        values.put( "name", user.getName() );
        values.put( "surname", user.getSurname() );
        values.put( "phone", user.getPhone() );
        values.put( "category", user.getCategory() );
        values.put( "date", user.getDate() );
        values.put( "hour", user.getHour());

        String[] args = { String.valueOf( user.getId() ) };

        return db.update( Util.TABLE_USER, values, "id=?", args );
    }

    public long deleteQuote( User user) {
        db = dbHlpr.getWritableDatabase();

        String[] args = { String.valueOf( user.getId() ) };

        return db.delete( Util.TABLE_USER, "id=?", args );
    }
}