package com.company.go.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.company.go.R;

public class EditPassword extends Activity {
    private ImageView btnBack;
    private TextView btnLogout;
    private LinearLayout btnSave;
    private ConstraintLayout mainScreen;
    private LinearLayout loginScreen;
    private LinearLayout profileScreen;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnLogout =(TextView) findViewById(R.id.btnLogout);
        btnSave = (LinearLayout)findViewById(R.id.btnSave);
        mainScreen =(ConstraintLayout) findViewById(R.id.activity_main);
        loginScreen =(LinearLayout) findViewById(R.id.activity_login);
        profileScreen = (LinearLayout)findViewById(R.id.activity_profile);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent_EditPassword2Profile = new Intent(EditPassword.this, profileScreen.getClass());
                startActivity(myIntent_EditPassword2Profile);
                finish();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent_EditPassword2Login = new Intent(EditPassword.this, loginScreen.getClass());
                startActivity(myIntent_EditPassword2Login);
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent_EditPassword2Profile = new Intent(EditPassword.this, profileScreen.getClass());
                startActivity(myIntent_EditPassword2Profile);
                finish();
            }
        });
    }
}
