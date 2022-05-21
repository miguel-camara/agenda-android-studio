package com.app.cita_sqlite;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.cita_sqlite.controller.QuoteController;
import com.app.cita_sqlite.model.Quote;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private List<Quote> quoteList;
    private RecyclerView recyclerView;
    private QuoteAdapter quoteAdapter;
    private QuoteController quoteController;
    private FloatingActionButton fabAddQuote;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        quoteController = new QuoteController( MainActivity.this );

        recyclerView = findViewById( R.id.recyclerViewQuote );
        fabAddQuote = findViewById( R.id.fabAddQuote );

        quoteList = new ArrayList<>();

        quoteAdapter = new QuoteAdapter( quoteList );

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager( getApplicationContext() );
        recyclerView.setLayoutManager( mLayoutManager );
        recyclerView.setItemAnimator( new DefaultItemAnimator() );
        recyclerView.setAdapter( quoteAdapter );

        refreshQuoteList();

        recyclerView.addOnItemTouchListener( new RecyclerTouchListener( getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position ) {
                Quote readQuote = quoteList.get( position );

                Intent intent = new Intent( MainActivity.this, EditQuoteActivity.class );
                intent.putExtra("id", readQuote.getId() );
                intent.putExtra( "name", readQuote.getName() );
                intent.putExtra( "surname", readQuote.getSurname() );
                intent.putExtra( "phone", readQuote.getPhone() );
                intent.putExtra( "category", readQuote.getCategory() );
                intent.putExtra( "date", readQuote.getDate() );
                intent.putExtra( "hour", readQuote.getHour() );
                startActivity( intent );
            }

            @Override
            public void onLongClick( View view, int position ) {
                Quote deleteQuote = quoteList.get( position );

                AlertDialog dialog = new AlertDialog
                        .Builder( MainActivity.this )
                        .setPositiveButton( "Sí, eliminar", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick( DialogInterface dialog, int which ) {
                                quoteController.deleteQuote( deleteQuote );
                                refreshQuoteList();
                            }

                        })
                        .setNegativeButton( "Cancelar", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick( DialogInterface dialog, int which ) {
                                dialog.dismiss();
                            }
                        })
                        .setTitle( "Confirmar" )
                        .setMessage( "¿Eliminar a " + deleteQuote.getName() + "?")
                        .create();
                dialog.show();

            }
        }));

        fabAddQuote.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent( MainActivity.this, AddQuoteActivity.class );
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshQuoteList();
    }

    public void refreshQuoteList() {
        if ( quoteAdapter == null ) return;

        quoteList = quoteController.readQuote();

        quoteAdapter.setQuoteList(quoteList);
        quoteAdapter.notifyDataSetChanged();
    }
}