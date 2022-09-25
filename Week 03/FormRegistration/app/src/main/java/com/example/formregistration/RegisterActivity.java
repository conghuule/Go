package com.example.formregistration;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

                String dayDisplay = "";
                String monthDisplay = "";

                dayDisplay = (dayOfMonth < 10) ? String.format("0%s", dayOfMonth) : String.valueOf(dayOfMonth);
                monthDisplay = (month1 < 10) ? String.format("0%s", month1) : String.valueOf(month1);

                String date = dayDisplay + "/" + monthDisplay + "/" + year1;
                edtBirthdate.setText(date);
            }, year, month, day);
            datePickerDialog.show();
        });
    }

    private void initDateChange() {
        edtBirthdate.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "ddmmyyyy";
            private Calendar cal = Calendar.getInstance();

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @SuppressLint("DefaultLocale")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                    String cleanC = current.replaceAll("[^\\d.]|\\.", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8) {
                        clean = clean + ddmmyyyy.substring(clean.length());
                    } else {
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day = Integer.parseInt(clean.substring(0, 2));
                        int mon = Integer.parseInt(clean.substring(2, 4));
                        int year = Integer.parseInt(clean.substring(4, 8));

                        mon = mon < 1 ? 1 : Math.min(mon, 12);
                        cal.set(Calendar.MONTH, mon - 1);
                        year = (year < 1900) ? 1900 : Math.min(year, 2100);
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = Math.min(day, cal.getActualMaximum(Calendar.DATE));
                        clean = String.format("%02d%02d%02d", day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = Math.max(sel, 0);
                    current = clean;
                    edtBirthdate.setText(current);
                    edtBirthdate.setSelection(Math.min(sel, current.length()));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
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

    boolean isValidDate(String format, String value) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return date != null;
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

        if (!isValidDate("dd/MM/yyyy", edtBirthdate.getText().toString())) {
            Toast.makeText(RegisterActivity.this, "Your birthdate is not invalid", Toast.LENGTH_LONG).show();
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

        initDateChange();
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

