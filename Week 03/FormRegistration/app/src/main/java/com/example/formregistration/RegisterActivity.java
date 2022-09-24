package com.example.formregistration;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Calendar;

public class RegisterActivity extends Activity {

    private EditText edtUsername;
    private EditText edtPassword1;
    private EditText edtPassword2;
    private EditText edtBirthdate;
    private CheckBox cbTennis;
    private CheckBox cbFurbal;
    private CheckBox cbOther;
    private RadioGroup rgGender;

    private void initPickDate() {
        Button btnPickDate = (Button) findViewById(R.id.btnSelectDate);
        Calendar calendar = Calendar.getInstance();
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int month = calendar.get(Calendar.MONTH);
        final int year = calendar.get(Calendar.YEAR);

        btnPickDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this, (view, year1, month1, dayOfMonth) -> {
                month1 += 1;
                String date = dayOfMonth + "/" + month1 + "/" + year1;
                edtBirthdate.setText(date);
            }, year, month, day);
            datePickerDialog.show();
        });
    }

    private void initReset() {
        Button btnReset = (Button) findViewById(R.id.btnReset);
        btnReset.setOnClickListener(v -> {
            edtUsername.setText("");
            edtPassword1.setText("");
            edtPassword2.setText("");
            edtBirthdate.setText("");
            cbTennis.setChecked(false);
            cbFurbal.setChecked(false);
            cbOther.setChecked(false);
            rgGender.check(R.id.rbMale);
        });
    }

    boolean isValidData() {

        if (edtUsername.getText().toString().isEmpty() || edtPassword1.getText().toString().isEmpty() || edtPassword2.getText().toString().isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Please fill in the required fields", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!edtPassword1.getText().toString().equals(edtPassword2.getText().toString())) {
            Toast.makeText(RegisterActivity.this, "Please re-type your password.", Toast.LENGTH_LONG).show();
            return false;
        }

        if (edtBirthdate.getText().toString().isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Please choose your birthdate.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private String getHobbies() {
        StringBuilder hobbies = new StringBuilder();
        if (cbTennis.isChecked()) hobbies.append("tennis, ");
        if (cbFurbal.isChecked()) hobbies.append("furbal, ");
        if (cbOther.isChecked()) hobbies.append("others, ");
        if (hobbies.length() > 2) {
            hobbies.setLength(hobbies.length() - 2);
        }
        return hobbies.toString();
    }

    private String getGender() {
        int selectedGenderId = rgGender.getCheckedRadioButtonId();
        RadioButton selectedGender = (RadioButton) findViewById(selectedGenderId);
        return selectedGender.getText().toString();
    }

    private void initSignup() {
        Button btnSignup = (Button) findViewById(R.id.btnSignUp);
        btnSignup.setOnClickListener(v -> {
            if (!isValidData()) {
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putString("username", edtUsername.getText().toString());
            bundle.putString("password", edtPassword1.getText().toString());
            bundle.putString("birthdate", edtBirthdate.getText().toString());
            bundle.putString("gender", getGender());
            bundle.putString("hobbies", getHobbies());

            Intent intent = new Intent(RegisterActivity.this, ResultActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }

    private void initGender() {
        rgGender = (RadioGroup) findViewById(R.id.rgGender);
        rgGender.check(R.id.rbMale);
    }

    private void initComponents() {
        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPassword1 = (EditText) findViewById(R.id.inputPassword);
        edtPassword2 = (EditText) findViewById(R.id.inputRetype);
        edtBirthdate = (EditText) findViewById(R.id.inputDate);
        cbTennis = (CheckBox) findViewById(R.id.cbTennis);
        cbFurbal = (CheckBox) findViewById(R.id.cbFurbal);
        cbOther = (CheckBox) findViewById(R.id.cbOthers);

        initPickDate();
        initReset();
        initSignup();
        initGender();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_form);
        initComponents();
    }
}