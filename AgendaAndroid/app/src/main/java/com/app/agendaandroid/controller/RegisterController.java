package com.app.agendaandroid.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.app.agendaandroid.helper.SQLite_OpenHelper;
import com.app.agendaandroid.model.Regiter;
import com.app.agendaandroid.util.Util;

import java.util.ArrayList;
import java.util.List;

public class RegisterController {
    SQLite_OpenHelper dbHlpr;
    SQLiteDatabase db;

    ContentValues values;

    List<Regiter> regiters;

    public RegisterController( Context context ) {
        dbHlpr = new SQLite_OpenHelper( context );
    }

    public long createRegister( Regiter regiter) {
        db = dbHlpr.getWritableDatabase();

        values = new ContentValues();
        values.put( "name", regiter.getName() );
        values.put( "phone", regiter.getPhone() );
        values.put( "email", regiter.getEmail() );
        values.put( "password", regiter.getPassword() );

        return db.insert( Util.TABLE_REGISTER, null, values);
    }

    public List<Regiter> readRegister() {
        regiters = new ArrayList<>();

        db = dbHlpr.getReadableDatabase();

        Cursor cursor = db.query( Util.TABLE_REGISTER, null, null, null, null, null, null);

        if ( cursor.moveToFirst() ) {
            do {
                Regiter regiter = new Regiter( cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4) );
                regiters.add( regiter );
            } while (cursor.moveToNext());
        }

        cursor.close();

        return regiters;
    }

    public boolean login(Regiter regiter) {
        boolean exist;
        String []columns = { "email", "password" };
        String [] args = { regiter.getEmail(), regiter.getPassword() };

        db = dbHlpr.getReadableDatabase();
        Cursor cursor = db.query( Util.TABLE_REGISTER, columns, "email LIKE ? AND password LIKE ?", args, null, null, null);
        exist = cursor.moveToFirst();
        cursor.close();

        return exist;
    }

    public boolean existRegister(Regiter regiter) {
        boolean exist;
        String []columns = { "email" };
        String [] args = { regiter.getEmail() };

        db = dbHlpr.getReadableDatabase();
        Cursor cursor = db.query( Util.TABLE_REGISTER, columns, "email LIKE ?", args, null, null, null);
        exist = cursor.moveToFirst();
        cursor.close();

        return exist;
    }

    public long updateRegister( Regiter regiter) {
        db = dbHlpr.getWritableDatabase();

        values = new ContentValues();

        values.put( "name", regiter.getName() );
        values.put( "phone", regiter.getPhone() );
        values.put( "email", regiter.getEmail() );
        values.put( "password", regiter.getPassword() );

        String[] args = { String.valueOf( regiter.getId() ) };

        return db.update( Util.TABLE_REGISTER, values, "id=?", args );
    }

    public long deleteRegister( Regiter regiter) {
        db = dbHlpr.getWritableDatabase();

        String[] args = { String.valueOf( regiter.getId() ) };

        return db.delete( Util.TABLE_REGISTER, "id=?", args );
    }
}