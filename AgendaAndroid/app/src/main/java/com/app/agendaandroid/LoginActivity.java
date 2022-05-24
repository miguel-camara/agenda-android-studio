package com.app.agendaandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.agendaandroid.controller.UserController;
import com.app.agendaandroid.model.User;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    TextView tvRegister;
    EditText etLoginEmail;
    EditText etLoginPassword;

    UserController userController;
    User user;
    long id;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        userController = new UserController( this );

        btnLogin = findViewById( R.id.btnLogin );
        tvRegister = findViewById( R.id.tvRegister );
        etLoginEmail = findViewById( R.id.etLoginEmail );
        etLoginPassword = findViewById( R.id.etLoginPassword );

        btnLogin.setOnClickListener( view -> {
            if ( validateForm() ) {
                 id = userController.login( user );
                 if (id != -1) goToMain();
                else Toast.makeText(this, "¡Correo o Contraseña Incorrecta!", Toast.LENGTH_SHORT).show();
            }
        });

        tvRegister.setOnClickListener( view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class );
            startActivity( intent );
            finish();
        });
    }

    public void goToMain(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class );
        intent.putExtra("id", id );
        startActivity( intent );
        Log.i(null, "ID: " + id);

        finish();
    }

    public boolean validateForm() {
        etLoginPassword.setError( null );
        etLoginEmail.setError( null );

        boolean isEmpty = true;
        String password = etLoginPassword.getText().toString();
        String email = etLoginEmail.getText().toString();

        password = password.trim();

        if ( password.isEmpty() ) {
            etLoginPassword.setError( getString( R.string.field ) );
            etLoginPassword.requestFocus();
            isEmpty = false;
        }

        email = email.trim();

        if (email.isEmpty()) {
            etLoginEmail.setError( getString( R.string.field ) );
            etLoginEmail.requestFocus();
            isEmpty = false;
        } else if(!Patterns.EMAIL_ADDRESS.matcher( email ).matches()) {
            etLoginEmail.setError( "¡Correo NO válido!" );
            etLoginEmail.requestFocus();
            isEmpty = false;
        }

        user = new User( "", "", email, password );

        return isEmpty;
    }
}