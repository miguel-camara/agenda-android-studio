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

import java.util.Arrays;
import java.util.Locale;

public class EditUserActivity extends AppCompatActivity  implements ValidationsHelper, DateHelper, View.OnClickListener {

    private EditText etEditName, etEditSurname, etEditPhone, etEditDate, etEditHour;
    private Spinner spinnerEditCategory;

    private Button btnSaveChanges, btnCancelEdition;
    private User user;
    private UserController userController;
    private ImageButton ibEditDate, ibEditHour;

    private User updateUser;

    final String[] data = new String[]{"Actividad Física", "Trabajo", "Compras",
            "Recreativo", "Otros"};

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        Bundle extras = getIntent().getExtras();

        if (extras == null) {
            finish();
            return;
        }

        init();

        userController = new UserController(this);

        long id = extras.getLong("id");
        String updateName = extras.getString("name");
        String surname = extras.getString("surname");
        String phone = extras.getString("phone");
        String category = extras.getString("category");
        String date = extras.getString("date");
        String hour = extras.getString("hour");

        user = new User( id, updateName, surname, phone, category, date, hour );

        etEditName.setText( user.getName() );
        etEditSurname.setText( user.getSurname() );
        etEditPhone.setText( user.getPhone() );
        spinnerEditCategory.setSelection( getCategory( user.getCategory() ) );
        etEditDate.setText( user.getDate() );
        etEditHour.setText( user.getHour() );

        ibEditDate.setOnClickListener( this );
        ibEditHour.setOnClickListener(this);

        btnCancelEdition.setOnClickListener( this );
        btnSaveChanges.setOnClickListener( this );
    }

    private void init() {
        etEditName = findViewById(R.id.etEditName);
        etEditSurname = findViewById(R.id.etEditSurname);
        etEditPhone = findViewById(R.id.etEditPhone);
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


        int dates [] = parseDate( user.getDate() );

        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                etEditDate.setText(date);
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(EditUserActivity.this, onDateSetListener, dates[2], dates[1] - 1, dates[0]);
        datePickerDialog.setTitle( "Fecha" );
        datePickerDialog.show();
    }


    private void editHour () {

        int hours[] = parseHour( user.getHour() );

        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                etEditHour.setText( String.format(Locale.getDefault(), "%02d:%02d", hour, minute) );
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog( this, onTimeSetListener, hours[0], hours[1], true);
        timePickerDialog.setTitle( "Hora" );
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
        long row = userController.updateQuote(updateUser);

        if (row != 1) Toast.makeText(EditUserActivity.this, "Error guardando cambios. Intente de nuevo.", Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(this, "Guardado", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private boolean validateForm () {
        boolean isEmpty = true;

        etEditName.setError(null);
        etEditSurname.setError(null);
        etEditPhone.setError(null);
        etEditDate.setError(null);
        etEditHour.setError(null);

        String updateName = etEditName.getText().toString();
        String updateSurname = etEditSurname.getText().toString();
        String updatePhone = etEditPhone.getText().toString();
        String updateCategory = spinnerEditCategory.getSelectedItem().toString();
        String updateDate = etEditDate.getText().toString();
        String updateHour = etEditHour.getText().toString();

        // Update Name
        updateName = updateName.trim();

        // not is Empty
        if (updateName.isEmpty()) {
            etEditName.setError("¡Escribe un nombre!");
            etEditName.requestFocus();
            isEmpty = false;
        } else if (!updateName.matches(NAME)) {
            etEditName.setError("¡Nombre Incorrecto!");
            etEditName.requestFocus();
            isEmpty = false;
        }

        // convert Name
        if (isEmpty) {
            updateName = updateName.toLowerCase(Locale.ROOT);
            updateName = Character.toUpperCase(updateName.charAt(0)) + updateName.substring(1);
        }

        // Update Surname
        updateSurname = updateSurname.trim();

        // not is Empty
        if (updateSurname.isEmpty()) {
            etEditSurname.setError("¡Escribe un apellido!");
            etEditSurname.requestFocus();
            isEmpty = false;
        }

        // not is Surname
        if (!updateSurname.matches(NAME)) {
            etEditSurname.setError("¡Apellido Incorrecto!");
            etEditSurname.requestFocus();
            isEmpty = false;
        }

        // convert Surname
        if ( isEmpty ) {
            updateSurname = updateSurname.toLowerCase( Locale.ROOT );
            updateSurname = Character.toUpperCase( updateSurname.charAt( 0 ) ) + updateSurname.substring( 1 );
        }

        // Update Phone
        updatePhone = updatePhone.trim();

        // not is Empty
        if ( updatePhone.isEmpty() ) {
            etEditPhone.setError("¡Escribe un telefono!");
            etEditPhone.requestFocus();
            isEmpty = false;
        }

        // not is Number
        if( !updatePhone.matches( NUMBER ) ) {
            etEditPhone.setError("¡Telefono Incorrecto!");
            etEditPhone.requestFocus();
            isEmpty = false;
        }

        // wrong length
        if ( updatePhone.length() != 10 ) {
            etEditPhone.setError("¡Escribe 10 digitos!");
            etEditPhone.requestFocus();
            isEmpty = false;
        }

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

        updateUser = new User( user.getId(), updateName, updateSurname, updatePhone, updateCategory, updateDate, updateHour );

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