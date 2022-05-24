package com.app.agendaandroid.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.app.agendaandroid.helper.SQLite_OpenHelper;
import com.app.agendaandroid.model.Event;
import com.app.agendaandroid.model.Favorite;
import com.app.agendaandroid.model.User;
import com.app.agendaandroid.util.Util;

import java.util.ArrayList;
import java.util.List;

public class FavoriteController {
    SQLite_OpenHelper dbHlpr;
    SQLiteDatabase db;

    ContentValues values;

    List<Favorite> favoriteList;

    public FavoriteController(Context context) {
        dbHlpr = new SQLite_OpenHelper( context );
    }

    public boolean isFavorite( Favorite favorite ) {

        String [] args = {String.valueOf(favorite.getId_user()), String.valueOf(favorite.getId_event())};
        String [] columns = { "id", "id_user", "id_event" };

        db = dbHlpr.getReadableDatabase();

        Cursor cursor = db.query( Util.TABLE_FAVORITE, columns, "id_user LIKE ? AND id_event LIKE ?", args, null, null, null);

        long id = -1;

        boolean isFav = cursor.moveToFirst();

        cursor.close();

        return isFav;
    }

    public Favorite getFavorite( Favorite favorite ) {

        String [] args = {String.valueOf(favorite.getId_user()), String.valueOf(favorite.getId_event())};
        String [] columns = { "id", "id_user", "id_event" };

        db = dbHlpr.getReadableDatabase();

        Cursor cursor = db.query( Util.TABLE_FAVORITE, columns, "id_user LIKE ? AND id_event LIKE ?", args, null, null, null);

        Favorite f = null;

        if ( cursor.moveToFirst() ) {
                f = new Favorite( cursor.getLong(0), cursor.getInt(1), cursor.getInt(2) );
        }

        cursor.close();

        return f;
    }

    public long createFavorite( Favorite favorite ) {
        db = dbHlpr.getWritableDatabase();

        values = new ContentValues();
        values.put( "id_user", favorite.getId_user() );
        values.put( "id_event", favorite.getId_event() );

        return db.insert( Util.TABLE_FAVORITE, null, values);
    }

    public List<Favorite> readFavorite() {
        favoriteList = new ArrayList<>();

        db = dbHlpr.getReadableDatabase();

        Cursor cursor = db.query( Util.TABLE_FAVORITE, null, null, null, null, null, null);

        if ( cursor.moveToFirst() ) {
            do {
                Favorite favorite = new Favorite( cursor.getLong(0), cursor.getInt(1), cursor.getInt(2) );
                favoriteList.add( favorite );
            } while (cursor.moveToNext());
        }

        cursor.close();

        return favoriteList;
    }

    public long updateFavorite( Favorite favorite ) {
        db = dbHlpr.getWritableDatabase();

        values = new ContentValues();

        values.put( "id_user", favorite.getId_user() );
        values.put( "id_event", favorite.getId_event() );

        String[] args = { String.valueOf( favorite.getId() ) };

        return db.update( Util.TABLE_FAVORITE, values, "id=?", args );
    }

    public long deleteFavorite( Favorite favorite ) {
        db = dbHlpr.getWritableDatabase();

        Favorite f = getFavorite( favorite );

        String[] args = { String.valueOf( f.getId() )};

        return db.delete( Util.TABLE_FAVORITE, "id=?", args );
    }
}