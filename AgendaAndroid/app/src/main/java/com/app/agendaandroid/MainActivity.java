package com.app.agendaandroid;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.app.agendaandroid.controller.EventController;
import com.app.agendaandroid.controller.FavoriteController;
import com.app.agendaandroid.model.Event;
import com.app.agendaandroid.model.Favorite;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Event> eventList;
    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private EventController eventController;
    private FloatingActionButton fabAddQuote;
    FavoriteController favoriteController;
    private long id;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        Bundle extras = getIntent().getExtras();

        eventController = new EventController( MainActivity.this );
        favoriteController = new FavoriteController( MainActivity.this );

        recyclerView = findViewById( R.id.recyclerViewUser );
        fabAddQuote = findViewById( R.id.fabAddUser );

        eventList = new ArrayList<>();

        eventAdapter = new EventAdapter( eventList, this, extras.getLong( "id" ) );

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager( getApplicationContext() );
        recyclerView.setLayoutManager( mLayoutManager );
        recyclerView.setItemAnimator( new DefaultItemAnimator() );
        recyclerView.setAdapter( eventAdapter );

        refreshEventList();

        recyclerView.addOnItemTouchListener( new RecyclerTouchListener( getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position ) { }

            @Override
            public void onLongClick( View view, int position ) {
                Event deleteEvent = eventList.get( position );

                AlertDialog dialog = new AlertDialog
                        .Builder( MainActivity.this )
                        .setPositiveButton( "Sí, eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick( DialogInterface dialog, int which ) {
                                favoriteController.readFavorite( deleteEvent.getId() ).forEach( favorite -> {
                                    favoriteController.deleteFavorite( favorite );
                                } );

                                eventController.deleteQuote(deleteEvent);

                                refreshEventList();
                            }
                        })
                        .setNegativeButton( "Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick( DialogInterface dialog, int which ) {
                                dialog.dismiss();
                            }
                        })
                        .setTitle( "Confirmar" )
                        .setMessage( "¿Eliminar " + deleteEvent.getCategory() + "?")
                        .create();
                dialog.show();
            }
        }));

        fabAddQuote.setOnClickListener( view ->  {
                Intent intent = new Intent( MainActivity.this, AddUserActivity.class );
                startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshEventList();
    }

    public void refreshEventList() {
        if ( eventAdapter == null ) return;

        eventList = eventController.readEvent();

        eventAdapter.setEventList(eventList);
        eventAdapter.notifyDataSetChanged();
    }
}