package com.company.go.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.company.go.Models.Car;
import com.company.go.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditProfile2 extends Fragment {
    private FirebaseFirestore db;

    public static com.company.go.Fragments.Account newInstance() {
        com.company.go.Fragments.Account fragment = new com.company.go.Fragments.Account();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_editprofile1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db.collection("cars")
                .document("207TlM6yP0CX3M0Fr03r")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            Car car = document.toObject(Car.class);
                            if (document.exists()) {
                                Card card = Card.newInstance(car);
                                getChildFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.testCard, card)
                                        .commit();
                            } else {
                                Log.d("Error", "No such document");
                            }
                        } else {
                            Log.d("Error", "get failed with ", task.getException());
                        }
                    }
                });

        ImageView btnBack = view.findViewById(R.id.btnBack);
        TextView btnLogout =view.findViewById(R.id.btnLogout);
        LinearLayout btnSave=view.findViewById(R.id.btnSave);
        ImageView btnCamera1 =view.findViewById(R.id.btnCamera1);
        ImageView btnCamera2 =view.findViewById(R.id.btnCamera2);
        LinearLayout loginScreen =view.findViewById(R.id.activity_login);
        LinearLayout editProfile1Screen=view.findViewById(R.id.activity_editPro1);
        LinearLayout editProfile3Screen=view.findViewById(R.id.activity_editPro3);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent_editProfile22EditProfile1 = new Intent(getActivity(), editProfile1Screen.getClass());
                startActivity(myIntent_editProfile22EditProfile1);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent_Profile2Login = new Intent(getActivity(), loginScreen.getClass());
                startActivity(myIntent_Profile2Login);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent_EditProfile22EditProfile3 = new Intent(getActivity(), editProfile3Screen.getClass());
                startActivity(myIntent_EditProfile22EditProfile3);
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
