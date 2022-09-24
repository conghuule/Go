package com.example.formregistration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends Activity {
    private TextView resultUsername;
    private TextView resultPassword;
    private TextView resultBirthdate;
    private TextView resultGender;
    private TextView resultHobbies;
    Button btnExit;

    private void initComponents() {
        resultUsername = (TextView) findViewById(R.id.resultUsername);
        resultPassword = (TextView) findViewById(R.id.resultPassword);
        resultBirthdate = (TextView) findViewById(R.id.resultBirthdate);
        resultGender = (TextView) findViewById(R.id.resultGender);
        resultHobbies = (TextView) findViewById(R.id.resultHobbies);
        btnExit = (Button) findViewById(R.id.btnExit);
        btnExit.setOnClickListener(v -> finishAffinity());
    }

    private void loadDataFromRegisterForm() {
        Intent data = getIntent();
        Bundle bundle = data.getExtras();

        StringBuilder password = new StringBuilder();
        int passwordSize = bundle.getString("password").length();
        for (int i = 0; i < passwordSize; ++i) {
            password.append('*');
        }

        resultUsername.setText(bundle.getString("username"));
        resultPassword.setText(password);
        resultBirthdate.setText(bundle.getString("birthdate"));
        resultGender.setText(bundle.getString("gender"));
        resultHobbies.setText(bundle.getString("hobbies"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_form);
        initComponents();
        loadDataFromRegisterForm();
    }
}