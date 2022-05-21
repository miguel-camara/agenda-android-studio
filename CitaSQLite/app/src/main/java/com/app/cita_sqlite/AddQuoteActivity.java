package com.app.cita_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import com.app.cita_sqlite.controller.QuoteController;
import com.app.cita_sqlite.helper.DateHelper;
import com.app.cita_sqlite.helper.ValidationsHelper;
import com.app.cita_sqlite.model.Quote;

import java.util.Calendar;
import java.util.Locale;

public class AddQuoteActivity extends AppCompatActivity implements ValidationsHelper, DateHelper, View.OnClickListener {
    private Button btnAddQuote, btnCancel;

    private EditText etName, etSurname, etPhone, etDate, etHour;
    private QuoteController quoteController;
    private Spinner spinnerCategory;
    private ImageButton ibDate, ibHour;

    final String[] data = new String[]{"Actividad Física", "Trabajo", "Compras",
            "Recreativo", "Otros"};

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quote);

        init();

        ibDate.setOnClickListener( this );
        ibHour.setOnClickListener( this );
        btnAddQuote.setOnClickListener( this );
        btnCancel.setOnClickListener( this );

        quoteController = new QuoteController( this );
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

        btnAddQuote = findViewById( R.id.btnAddQuote );
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
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        datePickerDialog.getWindow().
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
//        timePickerDialog.
        timePickerDialog.show();
    }

    private void cancel() {
        finish();
    }

    private void addQuote() {
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
            return;
        }

        // not is Name
        if( !name.matches( NAME ) ) {
            etName.setError("¡Nombre Incorrecto!");
            etName.requestFocus();
            return;
        }

        // convert Name
        name = name.toLowerCase( Locale.ROOT );
        name = Character.toUpperCase( name.charAt( 0 ) ) + name.substring( 1 );

        // Update Surname
        surname = surname.trim();

        // not is Empty
        if ( surname.isEmpty() ) {
            etSurname.setError("¡Escribe un apellido!");
            etSurname.requestFocus();
            return;
        }

        // not is Surname
        if( !surname.matches( NAME ) ) {
            etSurname.setError("¡Apellido Incorrecto!");
            etSurname.requestFocus();
            return;
        }

        // convert Surname
        surname = surname.toLowerCase( Locale.ROOT );
        surname = Character.toUpperCase( surname.charAt( 0 ) ) + surname.substring( 1 );

        // Update Phone
        phone = phone.trim();

        // not is Empty
        if ( phone.isEmpty() ) {
            etPhone.setError("¡Escribe un telefono!");
            etPhone.requestFocus();
            return;
        }

        // not is Number
        if( !phone.matches( NUMBER ) ) {
            etPhone.setError("¡Telefono Incorrecto!");
            etPhone.requestFocus();
            return;
        }

        // wrong length
        if ( phone.length() != 10 ) {
            etPhone.setError("¡Escribe 10 digitos!");
            etPhone.requestFocus();
            return;
        }

        // Update Date
        date = date.trim();

        // not is Empty
        if ( date.isEmpty() ) {
            etDate.setError("¡Seleccione una fecha!");
            etDate.requestFocus();
            return;
        }

        // Update Hour
        hour = hour.trim();

        // not is Empty
        if ( hour.isEmpty() ) {
            etHour.setError("¡Seleccione una hora!");
            etHour.requestFocus();
            return;
        }

        Quote createQuote = new Quote( name, surname, phone, category, date, hour );

        long id = quoteController.createQuote( createQuote );

        if (id == -1) Toast.makeText(  this, "Error al guardar. Intenta de nuevo", Toast.LENGTH_SHORT ).show();
        else finish();
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
            case R.id.btnAddQuote:
                addQuote();
                break;
        }
    }
}