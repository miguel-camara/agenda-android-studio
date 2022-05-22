package com.app.agendaandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.app.agendaandroid.controller.UserController;
import com.app.agendaandroid.helper.DateHelper;
import com.app.agendaandroid.helper.ValidationsHelper;
import com.app.agendaandroid.model.User;

import java.util.Locale;

public class AddUserActivity extends AppCompatActivity implements ValidationsHelper, DateHelper, View.OnClickListener {
    private Button btnAddQuote, btnCancel;

    private EditText etName, etSurname, etPhone, etDate, etHour;
    private UserController userController;
    private Spinner spinnerCategory;
    private ImageButton ibDate, ibHour;

    private User createUser;

    final String[] data = new String[]{"Actividad Física", "Trabajo", "Compras",
            "Recreativo", "Otros"};

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        init();

        ibDate.setOnClickListener( this );
        ibHour.setOnClickListener( this );
        btnAddQuote.setOnClickListener( this );
        btnCancel.setOnClickListener( this );

        userController = new UserController( this );
    }

    private void init() {
        etName = findViewById( R.id.etName );
        etSurname = findViewById( R.id.etSurname );
        etPhone = findViewById( R.id.etPhone );
        spinnerCategory = findViewById( R.id.spinnerCategory );
        etDate = findViewById( R.id.etDate );
        etHour = findViewById( R.id.etHour );

        ibDate = findViewById( R.id.ibDate );
        ibHour = findViewById( R.id.ibHour );

        btnAddQuote = findViewById( R.id.btnAddUser);
        btnCancel = findViewById( R.id.btnCancel );

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, data);

        spinnerCategory.setAdapter(adapter);
    }

    private void openDate() {
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                etDate.setText(date);
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, onDateSetListener, YEAR, MONTH - 1, DAY);
        datePickerDialog.setTitle( "Fecha" );
        datePickerDialog.show();
    }


    private void openHour() {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                etHour.setText( String.format(Locale.getDefault(), "%02d:%02d", hour, minute) );
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog( this, onTimeSetListener, HOUR, MINUTE, true);
        timePickerDialog.setTitle( "Hora" );
        timePickerDialog.show();
    }

    private void cancel() {
        finish();
    }

    private void addQuote() {

        long id = userController.createQuote(createUser);

        if (id == -1) Toast.makeText(  this, "Error al guardar. Intenta de nuevo", Toast.LENGTH_SHORT ).show();
        else {
            Toast.makeText(this, "Guardado", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private boolean validateForm () {
        boolean isEmpty = true;

        etName.setError( null );
        etSurname.setError( null );
        etPhone.setError( null );
        etDate.setError( null );
        etHour.setError( null );

        String name = etName.getText().toString();
        String surname = etSurname.getText().toString();
        String phone = etPhone.getText().toString();
        String category = spinnerCategory.getSelectedItem().toString();
        String date = etDate.getText().toString();
        String hour = etHour.getText().toString();

        // Update Name
        name = name.trim();

        // not is Empty
        if ( name.isEmpty() ) {
            etName.setError("¡Escribe un nombre!");
            etName.requestFocus();
            isEmpty = false;
        } else if( !name.matches( NAME ) ) {
            etName.setError("¡Nombre Incorrecto!");
            etName.requestFocus();
            isEmpty = false;
        }

        // convert Name
        if ( isEmpty ) {
            name = name.toLowerCase( Locale.ROOT );
            name = Character.toUpperCase( name.charAt( 0 ) ) + name.substring( 1 );
        }

        // Update Surname
        surname = surname.trim();

        // not is Empty
        if ( surname.isEmpty() ) {
            etSurname.setError("¡Escribe un apellido!");
            etSurname.requestFocus();
            isEmpty = false;
        } else if( !surname.matches( NAME ) ) {
            etSurname.setError("¡Apellido Incorrecto!");
            etSurname.requestFocus();
            isEmpty = false;
        }

        // convert Surname
        if ( isEmpty ) {
            surname = surname.toLowerCase( Locale.ROOT );
            surname = Character.toUpperCase( surname.charAt( 0 ) ) + surname.substring( 1 );
        }

        // Update Phone
        phone = phone.trim();

        // not is Empty
        if ( phone.isEmpty() ) {
            etPhone.setError("¡Escribe un telefono!");
            etPhone.requestFocus();
            isEmpty = false;
        } else if( !phone.matches( NUMBER ) ) {
            etPhone.setError("¡Telefono Incorrecto!");
            etPhone.requestFocus();
            isEmpty = false;
        } else  if ( phone.length() != 10 ) {
            etPhone.setError("¡Escribe 10 digitos!");
            etPhone.requestFocus();
            isEmpty = false;
        }

        // Update Date
        date = date.trim();

        // not is Empty
        if ( date.isEmpty() ) {
            etDate.setError("¡Seleccione una fecha!");
            etDate.requestFocus();
            isEmpty = false;
        }

        // Update Hour
        hour = hour.trim();

        // not is Empty
        if ( hour.isEmpty() ) {
            etHour.setError("¡Seleccione una hora!");
            etHour.requestFocus();
            isEmpty = false;
        }

        createUser = new User( name, surname, phone, category, date, hour );

        return isEmpty;
    }

    @Override
    public void onClick(View view) {
        switch ( view.getId() ) {
            case R.id.ibDate:
                openDate();
                break;
            case R.id.ibHour:
                openHour();
                break;
            case R.id.btnCancel:
                cancel();
                break;
            case R.id.btnAddUser:
                if ( validateForm() ) addQuote();
                break;
        }
    }
}