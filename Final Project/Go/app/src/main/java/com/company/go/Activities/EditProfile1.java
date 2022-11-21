package com.company.go.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.company.go.Fragments.EditProfile2;
import com.company.go.R;

public class EditProfile1 extends Activity {
    private ImageView btnBack;
    private TextView btnLogout;
    private LinearLayout btnSave;
    private de.hdodenhof.circleimageview.CircleImageView btnCamera;
    private LinearLayout profileScreen;
    private LinearLayout loginScreen;
    private LinearLayout EditProfile2Screen;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnLogout =(TextView) findViewById(R.id.btnLogout);
        btnSave = (LinearLayout)findViewById(R.id.btnSave);
        btnCamera =(de.hdodenhof.circleimageview.CircleImageView)findViewById(R.id.btnCamera);
        profileScreen =(LinearLayout) findViewById(R.id.activity_profile);
        loginScreen =(LinearLayout) findViewById(R.id.activity_login);
        EditProfile2Screen = (LinearLayout)findViewById(R.id.activity_editPro2);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent_EditProfile12Profile = new Intent(EditProfile1.this, profileScreen.getClass());
                startActivity(myIntent_EditProfile12Profile);
                finish();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent_EditProfile12Login = new Intent(EditProfile1.this, loginScreen.getClass());
                startActivity(myIntent_EditProfile12Login);
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent_EditProfile12EditProfile2 = new Intent(EditProfile1.this, EditProfile2Screen.getClass());
                startActivity(myIntent_EditProfile12EditProfile2);
                finish();
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(i);
            }
        });
    }
}

