package com.company.go.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.company.go.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Account #newInstance} factory method to
 * create an instance of this fragment.
 */
public class Account extends Fragment {

    private ImageView btnBack;
    private TextView btnLogout;
    private LinearLayout btnEdit_Pro;
    private LinearLayout btnEdit_Pass;
    private ConstraintLayout mainScreen;
    private LinearLayout loginScreen;
    private LinearLayout editProfileScreen;
    private LinearLayout editPassScreen;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onCreate(Bundle savedlnstanceState) {

        super.onCreate(savedlnstanceState);

        btnBack = (ImageView) btnBack.findViewById(R.id.btnBack);
        btnLogout =(TextView) btnLogout.findViewById(R.id.btnLogout);
        btnEdit_Pro=(LinearLayout) btnEdit_Pro.findViewById(R.id.btnEditPro);
        btnEdit_Pass=(LinearLayout)btnEdit_Pass.findViewById(R.id.btnEditPass);
        mainScreen =(ConstraintLayout) mainScreen.findViewById(R.id.activity_main);
        loginScreen =(LinearLayout) loginScreen.findViewById(R.id.activity_login);
        editProfileScreen=(LinearLayout)editProfileScreen.findViewById(R.id.activity_editPro1);
        editPassScreen=(LinearLayout)editPassScreen.findViewById(R.id.activity_editPass);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent_Profile2Main = new Intent(getActivity(), mainScreen.getClass());
                startActivity(myIntent_Profile2Main);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent_Profile2Login = new Intent(getActivity(), loginScreen.getClass());
                startActivity(myIntent_Profile2Login);
            }
        });

        btnEdit_Pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent_Profile2EditPro = new Intent(getActivity(), editProfileScreen.getClass());
                startActivity(myIntent_Profile2EditPro);
            }
        });

        btnEdit_Pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent_Profile2EditPass = new Intent(getActivity(), editPassScreen.getClass());
                startActivity(myIntent_Profile2EditPass);
            }
        });
    }
}