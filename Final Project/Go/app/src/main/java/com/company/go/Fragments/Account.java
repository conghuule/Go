package com.company.go.Fragments;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.company.go.Activities.LoginActivity;
import com.company.go.Activities.MainActivity;
import com.company.go.Models.Car;
import com.company.go.R;
import com.company.go.Utils.PicassoTrustAll;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.io.Serializable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Account #newInstance} factory method to
 * create an instance of this fragment.
 */
public class Account extends Fragment {
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    public static Account newInstance() {
        Account fragment = new Account();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();

        ((MainActivity) requireActivity()).setActiveTab(R.id.account);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView username = view.findViewById(R.id.user_name);
        ImageView btnBack = view.findViewById(R.id.btnBack);
        TextView btnLogout = view.findViewById(R.id.btnLogout);
        LinearLayout btnEdit_Pro = view.findViewById(R.id.btnEditPro);
        LinearLayout btnEdit_Pass = view.findViewById(R.id.btnEditPass);
        TextView fullName = view.findViewById(R.id.full_name);
        TextView phone = view.findViewById(R.id.phone);
        TextView email = view.findViewById(R.id.email);
        ImageView avatar = view.findViewById(R.id.account_avatar);

        username.setText(auth.getCurrentUser().getDisplayName());
        PicassoTrustAll.getInstance(getActivity())
                .load(auth.getCurrentUser().getPhotoUrl())
                .fit().into(avatar);
        fullName.setText(auth.getCurrentUser().getDisplayName());
        phone.setText(auth.getCurrentUser().getPhoneNumber());
        email.setText(auth.getCurrentUser().getEmail());

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent_Profile2Login = new Intent(getActivity(), MainActivity.class);
                startActivity(myIntent_Profile2Login);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Intent myIntent_Profile2Login = new Intent(getActivity(), LoginActivity.class);
                startActivity(myIntent_Profile2Login);
            }
        });

        btnEdit_Pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();

                mainActivity.switchFragment(new EditProfile(), true);
            }
        });

        btnEdit_Pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.switchFragment(new EditPassword(), true);
            }
        });
    }
}