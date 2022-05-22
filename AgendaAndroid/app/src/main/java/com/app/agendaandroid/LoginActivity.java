package com.app.agendaandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.agendaandroid.controller.RegisterController;
import com.app.agendaandroid.model.Regiter;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    TextView tvRegister;
    EditText etLoginEmail;
    EditText etLoginPassword;

    RegisterController registerController;
    Regiter regiter;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        registerController = new RegisterController( this );

        btnLogin = findViewById( R.id.btnLogin );
        tvRegister = findViewById( R.id.tvRegister );
        etLoginEmail = findViewById( R.id.etLoginEmail );
        etLoginPassword = findViewById( R.id.etLoginPassword );

        btnLogin.setOnClickListener( view -> {
            if ( validateForm() ) {
                if ( registerController.login( regiter ) ) goToMain();
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
        startActivity( intent );
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

        regiter = new Regiter( "", email, password, "" );

        return isEmpty;
    }
}