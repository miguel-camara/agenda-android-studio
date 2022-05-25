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

import java.util.Arrays;
import java.util.Locale;

public class EditEventActivity extends AppCompatActivity  implements DateHelper, View.OnClickListener {

    private EditText etEditDate, etEditHour;
    private Spinner spinnerEditCategory;
    private Button btnSaveChanges, btnCancelEdition;
    private ImageButton ibEditDate, ibEditHour;

    private Event event;
    private Event updateEven;

    private EventController eventController;


    final String[] data = new String[]{"Actividad Física", "Trabajo", "Compras",
            "Recreativo", "Otros"};

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        Bundle extras = getIntent().getExtras();

        if (extras == null) {
            finish();
            return;
        }

        init();

        eventController = new EventController( this );

        long id = extras.getLong("id");
        String category = extras.getString("category");
        String date = extras.getString("date");
        String hour = extras.getString("hour");
        int favorite = extras.getInt( "favorite" );

        event = new Event( id, category, date, hour );

        spinnerEditCategory.setSelection( getCategory( event.getCategory() ) );
        etEditDate.setText( event.getDate() );
        etEditHour.setText( event.getHour() );

        ibEditDate.setOnClickListener( this );
        ibEditHour.setOnClickListener(this);

        btnCancelEdition.setOnClickListener( this );
        btnSaveChanges.setOnClickListener( this );
    }

    private void init() {
        spinnerEditCategory = findViewById(R.id.spinnerEditCategory);
        etEditDate = findViewById(R.id.etEditDate);
        etEditHour = findViewById(R.id.etEditHour);

        spinnerEditCategory = findViewById(R.id.spinnerEditCategory);

        ibEditDate = findViewById(R.id.ibEditDate);
        ibEditHour = findViewById(R.id.ibEditHour);

        btnCancelEdition = findViewById(R.id.btnCancelEdition);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, data);

        spinnerEditCategory.setAdapter(adapter);
    }

    private int[] parseDate( String date ) {
        String [] result = date.split("/");

        return Arrays.stream( result ).mapToInt(Integer::parseInt).toArray();
    }

    private int[] parseHour( String hour ) {
        String [] result = hour.split(":");

        return Arrays.stream( result ).mapToInt(Integer::parseInt).toArray();
    }

    private void editDate() {
        int dates [] = parseDate( event.getDate() );

        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                etEditDate.setText(date);
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(EditEventActivity.this, onDateSetListener, dates[2], dates[1] - 1, dates[0]);
        datePickerDialog.show();
    }


    private void editHour () {

        int hours[] = parseHour( event.getHour() );

        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                etEditHour.setText( String.format(Locale.getDefault(), "%02d:%02d", hour, minute) );
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog( this, onTimeSetListener, hours[0], hours[1], true);
        timePickerDialog.show();
    }

    private void cancelEdition() {
        finish();
    }

    private int getCategory(String category) {
        switch (category) {
            case "Actividad Física":
                return 0;
            case "Trabajo":
                return 1;
            case "Compras":
                return  2;
            case "Recreativo":
                return 3;
            case "Otros":
                return  4;
            default:
                return -1;
        }
    }

    private void saveChanges() {
        long row = eventController.updateEvent(updateEven);

        if (row != 1) Toast.makeText(EditEventActivity.this, "Error guardando cambios. Intente de nuevo.", Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(this, "Guardado", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private boolean validateForm () {
        boolean isEmpty = true;

        etEditDate.setError(null);
        etEditHour.setError(null);

        String updateCategory = spinnerEditCategory.getSelectedItem().toString();
        String updateDate = etEditDate.getText().toString();
        String updateHour = etEditHour.getText().toString();

        // Update Date
        updateDate = updateDate.trim();

        // not is Empty
        if ( updateDate.isEmpty() ) {
            etEditDate.setError("¡Seleccione una fecha!");
            etEditDate.requestFocus();
            isEmpty = false;
        }

        // Update Hour
        updateHour = updateHour.trim();

        // not is Empty
        if ( updateHour.isEmpty() ) {
            etEditHour.setError("¡Seleccione una hora!");
            etEditHour.requestFocus();
            isEmpty = false;
        }

        updateEven = new Event( event.getId(), updateCategory, updateDate, updateHour );

        return isEmpty;
    }

    @Override
    public void onClick(View view) {
        switch ( view.getId() ) {
            case R.id.ibEditDate:
                editDate();
                break;
            case R.id.ibEditHour:
                editHour();
                break;
            case R.id.btnCancelEdition:
                cancelEdition();
                break;
            case R.id.btnSaveChanges:
                if ( validateForm() ) saveChanges();
                break;
        }
    }
}