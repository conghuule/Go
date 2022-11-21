package com.company.go.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.company.go.Fragments.EditProfile3;
import com.company.go.R;

public class EditProfile2 extends Activity {
    private ImageView btnBack;
    private TextView btnLogout;
    private LinearLayout btnSave;
    private ImageView btnCamera1;
    private ImageView btnCamera2;
    private LinearLayout EditProfile1Screen;
    private LinearLayout loginScreen;
    private LinearLayout EditProfile3Screen;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnLogout =(TextView) findViewById(R.id.btnLogout);
        btnSave = (LinearLayout)findViewById(R.id.btnSave);
        btnCamera1 =(ImageView)findViewById(R.id.btnCamera1);
        btnCamera2 =(ImageView)findViewById(R.id.btnCamera2);
        EditProfile1Screen =(LinearLayout) findViewById(R.id.activity_editPro1);
        loginScreen =(LinearLayout) findViewById(R.id.activity_login);
        EditProfile3Screen = (LinearLayout)findViewById(R.id.activity_editPro3);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent_EditProfile22EditProfile1 = new Intent(EditProfile2.this, EditProfile1Screen.getClass());
                startActivity(myIntent_EditProfile22EditProfile1);
                finish();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent_EditProfile22Login = new Intent(EditProfile2.this, loginScreen.getClass());
                startActivity(myIntent_EditProfile22Login);
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent_EditProfile22EditProfile3 = new Intent(EditProfile2.this, EditProfile3Screen.getClass());
                startActivity(myIntent_EditProfile22EditProfile3);
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
