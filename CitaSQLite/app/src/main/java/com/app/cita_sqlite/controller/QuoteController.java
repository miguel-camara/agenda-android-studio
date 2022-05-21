package com.app.cita_sqlite.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.app.cita_sqlite.helper.SQLite_OpenHelper;
import com.app.cita_sqlite.model.Quote;

import java.util.ArrayList;
import java.util.List;

public class QuoteController {
    SQLite_OpenHelper dbHlpr;
    SQLiteDatabase db;

    ContentValues values;

    List<Quote> quoteList;

    public QuoteController( Context context) {
        dbHlpr = new SQLite_OpenHelper( context );
    }

    public long createQuote( Quote quote ) {
        db = dbHlpr.getWritableDatabase();

        values = new ContentValues();
        values.put( "name", quote.getName() );
        values.put( "surname", quote.getSurname() );
        values.put( "phone", quote.getPhone() );
        values.put( "category", quote.getCategory() );
        values.put( "date", quote.getDate() );
        values.put( "hour", quote.getHour() );

        return db.insert( SQLite_OpenHelper.TABLE_NAME, null, values);
    }

    public List<Quote> readQuote() {
        quoteList = new ArrayList<>();

        db = dbHlpr.getReadableDatabase();

        Cursor cursor = db.query( SQLite_OpenHelper.TABLE_NAME, null, null, null, null, null, null);

        if ( cursor.moveToFirst() ) {
            do {
                Quote quote = new Quote( cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6) );
                quoteList.add( quote );
            } while (cursor.moveToNext());
        }

        cursor.close();

        return quoteList;
    }

    public long updateQuote( Quote quote ) {
        db = dbHlpr.getWritableDatabase();

        values = new ContentValues();

        values.put( "name", quote.getName() );
        values.put( "surname", quote.getSurname() );
        values.put( "phone", quote.getPhone() );
        values.put( "category", quote.getCategory() );
        values.put( "date", quote.getDate() );
        values.put( "hour", quote.getHour());

        String[] args = { String.valueOf( quote.getId() ) };

        return db.update( SQLite_OpenHelper.TABLE_NAME, values, "id=?", args );
    }

    public long deleteQuote( Quote quote ) {
        db = dbHlpr.getWritableDatabase();

        String[] args = { String.valueOf( quote.getId() ) };

        return db.delete( SQLite_OpenHelper.TABLE_NAME, "id=?", args );
    }
}