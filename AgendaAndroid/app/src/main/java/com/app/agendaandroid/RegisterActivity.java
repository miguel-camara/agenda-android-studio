package com.app.agendaandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.agendaandroid.controller.RegisterController;
import com.app.agendaandroid.helper.ValidationsHelper;
import com.app.agendaandroid.model.Regiter;

import java.util.Locale;

public class RegisterActivity extends AppCompatActivity  implements ValidationsHelper {

    Button btnRegister;
    EditText etRegisterName;
    EditText etRegisterPhone;
    EditText etRegisterEmail;
    EditText etRegisterPassword;
    RegisterController registerController;
    Regiter regiter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerController = new RegisterController( this );

        etRegisterEmail = findViewById( R.id.etRegisterEmail );
        etRegisterPassword = findViewById( R.id.etRegisterPassword );
        etRegisterName = findViewById( R.id.etRegisterName );
        etRegisterPhone = findViewById( R.id.etRegisterPhone );
        btnRegister = findViewById( R.id.btnRegister );

        btnRegister.setOnClickListener( view -> {
            if ( validateForm() ) {
                if (registerController.existRegister( regiter ) ) {
                    Toast.makeText(this, "¡El Usuario ya Existe!", Toast.LENGTH_SHORT).show();
                    goToLogin();
                } else save();
            }
        } );
    }

    private void save() {
        long id = registerController.createRegister( regiter );

        if (id == -1) Toast.makeText(  this, "Error al guardar. Intenta de nuevo", Toast.LENGTH_SHORT ).show();
        else {
            Toast.makeText(  this, "Creado Correctamente", Toast.LENGTH_SHORT ).show();
            goToLogin();
        }

    }

    public void goToLogin(){
        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity( i );
        finish();
    }

    public boolean validateForm() {
        etRegisterName.setError( null );
        etRegisterPassword.setError( null );
        etRegisterPhone.setError( null );
        etRegisterEmail.setError( null );

        boolean isEmpty = true;
        String phone = etRegisterPhone.getText().toString();
        String password = etRegisterPassword.getText().toString();
        String email = etRegisterEmail.getText().toString();
        String name = etRegisterName.getText().toString();

        name = name.trim();

        if ( name.isEmpty() ) {
            etRegisterName.setError( getString( R.string.field ) );
            etRegisterName.requestFocus();
            isEmpty = false;
        } else if ( !name.matches( NAME ) ) {
            etRegisterName.setError( "Nombre Incorrecto" );
            etRegisterName.requestFocus();
            isEmpty = false;
        }

        if ( isEmpty ){
            name = name.toLowerCase( Locale.ROOT );
            name = Character.toUpperCase( name.charAt( 0 ) ) + name.substring( 1 );
        }

        phone = phone.trim();

        if ( phone.isEmpty() ) {
            etRegisterPhone.setError( getString( R.string.field ) );
            etRegisterPhone.requestFocus();
            isEmpty = false;
        } else if ( !phone.matches( NUMBER ) ) {
            etRegisterPhone.setError( "Numero Incorrecto" );
            etRegisterPhone.requestFocus();
            isEmpty = false;
        } else if ( phone.length() != 10 ) {
            etRegisterPhone.setError( "Escriba 10 digitos" );
            etRegisterPhone.requestFocus();
            isEmpty = false;
        }

        password = password.trim();

        if ( password.isEmpty() ) {
            etRegisterPassword.setError( getString( R.string.field ) );
            etRegisterPassword.requestFocus();
            isEmpty = false;
        } else if ( password.length() < 6 ) {
            etRegisterPassword.setError( "Minimo 6 caracteres" );
            etRegisterPassword.requestFocus();
            isEmpty = false;
        }

        email = email.trim();

        if (email.isEmpty()) {
            etRegisterEmail.setError( getString( R.string.field ) );
            etRegisterEmail.requestFocus();
            isEmpty = false;
        } else if(!Patterns.EMAIL_ADDRESS.matcher( email ).matches()) {
            etRegisterEmail.setError( "¡Correo NO válido!" );
            etRegisterEmail.requestFocus();
            isEmpty = false;
        }

        regiter = new Regiter( name, email, password, phone );

        return isEmpty;
    }
}