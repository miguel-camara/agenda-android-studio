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

import com.app.agendaandroid.controller.RegisterController;
import com.app.agendaandroid.controller.UserController;
import com.app.agendaandroid.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private List<User> userList;
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private UserController userController;
    private FloatingActionButton fabAddQuote;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        userController = new UserController( MainActivity.this );

        recyclerView = findViewById( R.id.recyclerViewUser);
        fabAddQuote = findViewById( R.id.fabAddUser);

        userList = new ArrayList<>();

        userAdapter = new UserAdapter(userList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager( getApplicationContext() );
        recyclerView.setLayoutManager( mLayoutManager );
        recyclerView.setItemAnimator( new DefaultItemAnimator() );
        recyclerView.setAdapter(userAdapter);

        refreshQuoteList();

        recyclerView.addOnItemTouchListener( new RecyclerTouchListener( getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position ) {
                User readUser = userList.get( position );

                Intent intent = new Intent( MainActivity.this, EditUserActivity.class );
                intent.putExtra("id", readUser.getId() );
                intent.putExtra( "name", readUser.getName() );
                intent.putExtra( "surname", readUser.getSurname() );
                intent.putExtra( "phone", readUser.getPhone() );
                intent.putExtra( "category", readUser.getCategory() );
                intent.putExtra( "date", readUser.getDate() );
                intent.putExtra( "hour", readUser.getHour() );
                startActivity( intent );
            }

            @Override
            public void onLongClick( View view, int position ) {
                User deleteUser = userList.get( position );

                AlertDialog dialog = new AlertDialog
                        .Builder( MainActivity.this )
                        .setPositiveButton( "Sí, eliminar", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick( DialogInterface dialog, int which ) {
                                userController.deleteQuote(deleteUser);
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
                        .setMessage( "¿Eliminar a " + deleteUser.getName() + "?")
                        .create();
                dialog.show();

            }
        }));

        fabAddQuote.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent( MainActivity.this, AddUserActivity.class );
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
        if ( userAdapter == null ) return;

        userList = userController.readQuote();

        userAdapter.setQuoteList(userList);
        userAdapter.notifyDataSetChanged();
    }
}