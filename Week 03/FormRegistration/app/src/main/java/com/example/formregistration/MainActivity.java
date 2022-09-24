package com.example.formregistration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends Activity {
    EditText etDate;
    Button btnSelectDate;
    DatePickerDialog.OnDateSetListener setListener;
    Button btnSignUp;
    EditText edtPassword;
    EditText edtRetype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtPassword = findViewById(R.id.inputPassword);
        edtRetype = findViewById(R.id.inputRetype);
        btnSignUp = findViewById(R.id.btnSignUp);
        etDate = findViewById(R.id.inputDate);
        btnSelectDate = findViewById(R.id.btnSelectDate);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, setListener, year, month, day);
                datePickerDialog.show();
            }
        });
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String formattedMonth = "" + month;
                String formattedDayOfMonth = "" + dayOfMonth;

                if (month < 10) {
                    formattedMonth = "0" + month;
                }
                if (dayOfMonth < 10) {

                    formattedDayOfMonth = "0" + dayOfMonth;
                }

                String date = formattedDayOfMonth + "/" + formattedMonth + "/" + year;
                etDate.setText(date);
            }
        };
    }

    public void CheckSignUp(View view) {
    if(view.equals(btnSignUp)) {
        if(checkRetype() == true && checkDate(String.valueOf(etDate)) == true){
            //To SIGNUP
        }else{
            if(checkRetype() == false){
                Toast.makeText(this, "Please check retype again! Retype don't like Password.", Toast.LENGTH_LONG).show();
            }
            if(checkDate(String.valueOf(etDate)) == false){
                Toast.makeText(this, "Please check date format!", Toast.LENGTH_LONG).show();
            }
        }
    }
    }
    public boolean checkRetype() {
        String password = edtPassword.getText().toString();
        String retype = edtRetype.getText().toString();
        if(password.equals(retype)){
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean checkDate(String strDate)
    {
        /* Check if date is 'null' */
        if (strDate.trim().equals(""))
        {
            return true;
        }
        /* Date is not 'null' */
        else
        {
            /*
             * Set preferred date format,
             * For example MM-dd-yyyy, MM.dd.yyyy,dd.MM.yyyy etc.*/
            SimpleDateFormat formatdate = new SimpleDateFormat("MM/dd/yyyy");
            formatdate.setLenient(false);
            /* Create Date object
             * parse the string into date
             */
            try
            {
                Date javaDate = formatdate.parse(strDate);
            }
            /* Date format is invalid */
            catch (ParseException e)
            {
                return false;
            }
            /* Return true if date format is valid */
            return true;
        }
    }
}