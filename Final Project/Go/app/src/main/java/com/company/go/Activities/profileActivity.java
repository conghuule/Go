package com.company.go.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.company.go.R;

public class profileActivity extends Activity {
    private ImageView btnBack;
    private ConstraintLayout mainScreen;
    private LinearLayout loginScreen;
    protected void onCreate(Bundle savedlnstanceState) {

        super.onCreate(savedlnstanceState);
        setContentView(R.layout.activity_profile);

        btnBack = (ImageView) findViewById(R.id.back_btn);
        mainScreen =(ConstraintLayout) findViewById(R.id.activity_main);
        loginScreen =(LinearLayout) findViewById(R.id.activity_login);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent_Profile2Main = new Intent(profileActivity.this, mainScreen.getClass());

                startActivityForResult(myIntent_Profile2Main, 0);
            }
        });
    }

}
