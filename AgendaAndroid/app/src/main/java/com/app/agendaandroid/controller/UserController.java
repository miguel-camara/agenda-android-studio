package com.app.agendaandroid.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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

    public long login( User  user ) {

        String [] args = { user.getEmail(), user.getPassword() };
        String [] columns = { "id", "email", "password" };
        db = dbHlpr.getReadableDatabase();
        Cursor cursor = db.query( Util.TABLE_USER, columns, "email LIKE ? AND password LIKE ?", args, null, null, null);

        long id = -1;

        if ( cursor.moveToFirst() ) {
            User nUser = new User( cursor.getLong(0), null, null, cursor.getString(1), cursor.getString(2) );
            id = nUser.getId();
        }

        cursor.close();

        return id;
    }

    public boolean userExists( User user ) {
        boolean exists;
        String []columns = { "email" };
        String [] args = { user.getEmail() };

        db = dbHlpr.getReadableDatabase();
        Cursor cursor = db.query( Util.TABLE_USER, columns, "email LIKE ?", args, null, null, null);
        exists = cursor.moveToFirst();
        cursor.close();

        return exists;
    }


    public long createUser( User user) {
        db = dbHlpr.getWritableDatabase();

        values = new ContentValues();
        values.put( "name", user.getName() );
        values.put( "phone", user.getPhone() );
        values.put( "email", user.getEmail() );
        values.put( "password", user.getPassword() );

        return db.insert( Util.TABLE_USER, null, values);
    }

    public List<User> readUser() {
        userList = new ArrayList<>();

        db = dbHlpr.getReadableDatabase();

        Cursor cursor = db.query( Util.TABLE_USER, null, null, null, null, null, null);

        if ( cursor.moveToFirst() ) {
            do {
                User user = new User( cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4) );
                userList.add( user );
            } while (cursor.moveToNext());
        }

        cursor.close();

        return userList;
    }

    public long updateUser( User user) {
        db = dbHlpr.getWritableDatabase();

        values = new ContentValues();

        values.put( "name", user.getName() );
        values.put( "phone", user.getPhone() );
        values.put( "email", user.getEmail() );
        values.put( "password", user.getPassword() );

        String[] args = { String.valueOf( user.getId() ) };

        return db.update( Util.TABLE_USER, values, "id=?", args );
    }

    public long deleteUser( User user ) {
        db = dbHlpr.getWritableDatabase();

        String[] args = { String.valueOf( user.getId() ) };

        return db.delete( Util.TABLE_USER, "id=?", args );
    }
}