package com.company.go.Fragments;

import android.content.Intent;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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
//
//        db.collection("cars")
//                .document("207TlM6yP0CX3M0Fr03r")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if (task.isSuccessful()) {
//                            DocumentSnapshot document = task.getResult();
//                            Car car = document.toObject(Car.class);
//                            if (document.exists()) {
//                                Card card = Card.newInstance(car);
//                                getChildFragmentManager()
//                                        .beginTransaction()
//                                        .replace(R.id.testCard, card)
//                                        .commit();
//                            } else {
//                                Log.d("Error", "No such document");
//                            }
//                        } else {
//                            Log.d("Error", "get failed with ", task.getException());
//                        }
//                    }
//                });

        ImageView btnBack = view.findViewById(R.id.btnBack);
        TextView btnLogout = view.findViewById(R.id.btnLogout);
        LinearLayout btnEdit_Pro = view.findViewById(R.id.btnEditPro);
        LinearLayout btnEdit_Pass = view.findViewById(R.id.btnEditPass);
        ConstraintLayout mainScreen = view.findViewById(R.id.activity_main);
        LinearLayout loginScreen = view.findViewById(R.id.activity_login);
        FrameLayout editProfileScreen = view.findViewById(R.id.fragment_editPro);
        LinearLayout editPassScreen = view.findViewById(R.id.fragment_editPass);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent myIntent_Profile2Main = new Intent(getActivity(), Main);
//                startActivity(myIntent_Profile2Main);
                getActivity().onBackPressed();
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
//                Log.d("Get class", String.valueOf(view.findViewById(R.id.btnBack)));
                MainActivity mainActivity = (MainActivity) getActivity();

                mainActivity.switchFragment(new EditProfile());
//                Intent myIntent_Profile2EditPro = new Intent(getActivity(), editProfileScreen.getClass());
//                startActivity(myIntent_Profile2EditPro);
            }
        });

        btnEdit_Pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d("Get class", String.valueOf(view));
//                Intent myIntent_Profile2EditPass = new Intent(getActivity(), editPassScreen.getClass());
//                startActivity(myIntent_Profile2EditPass);
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.switchFragment(new EditPassword());
            }
        });
    }
}