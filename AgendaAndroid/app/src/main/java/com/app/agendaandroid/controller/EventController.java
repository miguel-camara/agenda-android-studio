package com.app.agendaandroid.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.app.agendaandroid.helper.SQLite_OpenHelper;
import com.app.agendaandroid.model.Event;
import com.app.agendaandroid.model.User;
import com.app.agendaandroid.util.Util;

import java.util.ArrayList;
import java.util.List;

public class EventController {
    SQLite_OpenHelper dbHlpr;
    SQLiteDatabase db;

    ContentValues values;

    List<Event> eventList;

    public EventController(Context context) {
        dbHlpr = new SQLite_OpenHelper( context );
    }

    public long createEvent( Event event ) {
        db = dbHlpr.getWritableDatabase();

        values = new ContentValues();
        values.put( "category", event.getCategory() );
        values.put( "date", event.getDate() );
        values.put( "hour", event.getHour() );

        return db.insert( Util.TABLE_EVENT, null, values);
    }

    public List<Event> readEvent() {
        eventList = new ArrayList<>();

        db = dbHlpr.getReadableDatabase();

        Cursor cursor = db.query( Util.TABLE_EVENT, null, null, null, null, null, null);

        if ( cursor.moveToFirst() ) {
            do {
                Event event = new Event( cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getString(3) );
                eventList.add( event );
            } while (cursor.moveToNext());
        }

        cursor.close();

        return eventList;
    }

    public long updateEvent( Event event) {
        db = dbHlpr.getWritableDatabase();

        values = new ContentValues();

        values.put( "category", event.getCategory() );
        values.put( "date", event.getDate() );
        values.put( "hour", event.getHour() );

        String[] args = { String.valueOf( event.getId() ) };

        return db.update( Util.TABLE_EVENT, values, "id=?", args );
    }

    public long deleteQuote( Event event) {
        db = dbHlpr.getWritableDatabase();

        String[] args = { String.valueOf( event.getId() ) };

        return db.delete( Util.TABLE_EVENT, "id=?", args );
    }
}