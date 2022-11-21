package com.company.go.Fragments.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.company.go.R;

public class profileActivity extends Activity {
    private ImageView btnBack;
    private TextView btnLogout;
    private LinearLayout btnEdit_Pro;
    private LinearLayout btnEdit_Pass;
    private ConstraintLayout mainScreen;
    private LinearLayout loginScreen;
    private LinearLayout editProfileScreen;
    private LinearLayout editPassScreen;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnLogout =(TextView) findViewById(R.id.btnLogout);
        btnEdit_Pro=(LinearLayout) findViewById(R.id.btnEditPro);
        btnEdit_Pass=(LinearLayout)findViewById(R.id.btnEditPass);
        mainScreen =(ConstraintLayout) findViewById(R.id.activity_main);
        loginScreen =(LinearLayout) findViewById(R.id.activity_login);
        editProfileScreen=(LinearLayout)findViewById(R.id.activity_editPro1);
        editPassScreen=(LinearLayout)findViewById(R.id.activity_editPass);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent_Profile2Main = new Intent(profileActivity.this, mainScreen.getClass());
                startActivity(myIntent_Profile2Main);
                finish();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent_Profile2Login = new Intent(profileActivity.this, loginScreen.getClass());
                startActivity(myIntent_Profile2Login);
                finish();
            }
        });
        
        btnEdit_Pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent_Profile2EditPro = new Intent(profileActivity.this, editProfileScreen.getClass());
                startActivity(myIntent_Profile2EditPro);
                finish();
            }
        });

        btnEdit_Pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent_Profile2EditPass = new Intent(profileActivity.this, editPassScreen.getClass());
                startActivity(myIntent_Profile2EditPass);
                finish();
            }
        });
    }

}
