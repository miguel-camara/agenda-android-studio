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

import com.app.agendaandroid.controller.EventController;
import com.app.agendaandroid.helper.DateHelper;
import com.app.agendaandroid.model.Event;

import java.util.Locale;

public class AddEventActivity extends AppCompatActivity implements DateHelper, View.OnClickListener {
    private Button btnAddQuote, btnCancel;
    private Spinner spinnerCategory;
    private EditText etDate, etHour;
    private ImageButton ibDate, ibHour;

    private EventController eventController;

    private Event createEvent;

    final String[] data = new String[]{"Actividad Física", "Trabajo", "Compras",
            "Recreativo", "Otros"};

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        init();

        ibDate.setOnClickListener( this );
        ibHour.setOnClickListener( this );
        btnAddQuote.setOnClickListener( this );
        btnCancel.setOnClickListener( this );

        eventController = new EventController( this );
    }

    private void init() {
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
        timePickerDialog.show();
    }

    private void cancel() {
        finish();
    }

    private void addQuote() {

        long id = eventController.createEvent( createEvent );

        if (id == -1) Toast.makeText(  this, "Error al guardar. Intenta de nuevo", Toast.LENGTH_SHORT ).show();
        else {
            Toast.makeText(this, "Guardado", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private boolean validateForm () {
        boolean isEmpty = true;

        etDate.setError( null );
        etHour.setError( null );

        String category = spinnerCategory.getSelectedItem().toString();
        String date = etDate.getText().toString();
        String hour = etHour.getText().toString();

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

        createEvent = new Event( category, date, hour );

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