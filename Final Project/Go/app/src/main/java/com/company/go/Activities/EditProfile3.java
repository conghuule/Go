package com.company.go.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.company.go.R;

public class EditProfile3 extends Activity {
    private ImageView btnBack;
    private TextView btnLogout;
    private LinearLayout btnSave;
    private ImageView btnCamera1;
    private ImageView btnCamera2;
    private LinearLayout EditProfile2Screen;
    private LinearLayout loginScreen;
    private LinearLayout profileScreen;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnLogout =(TextView) findViewById(R.id.btnLogout);
        btnSave = (LinearLayout)findViewById(R.id.btnSave);
        btnCamera1 =(ImageView)findViewById(R.id.btnCamera1);
        btnCamera2 =(ImageView)findViewById(R.id.btnCamera2);
        EditProfile2Screen =(LinearLayout) findViewById(R.id.activity_editPro2);
        loginScreen =(LinearLayout) findViewById(R.id.activity_login);
        profileScreen = (LinearLayout)findViewById(R.id.activity_profile);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent_EditProfile32EditProfile2 = new Intent(EditProfile3.this, EditProfile2Screen.getClass());
                startActivity(myIntent_EditProfile32EditProfile2);
                finish();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent_EditProfile32Login = new Intent(EditProfile3.this, loginScreen.getClass());
                startActivity(myIntent_EditProfile32Login);
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent_EditProfile32Profile = new Intent(EditProfile3.this, profileScreen.getClass());
                startActivity(myIntent_EditProfile32Profile);
                finish();
            }
        });

        btnCamera1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(i);
            }
        });

        btnCamera2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(i);
            }
        });
    }
}
