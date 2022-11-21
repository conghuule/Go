package com.company.go.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.company.go.Models.Car;
import com.company.go.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditPassword #newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditPassword extends Fragment {

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
        return inflater.inflate(R.layout.fragment_editpassword, container, false);
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
        TextView btnLogout = view.findViewById(R.id.btnLogout);
        LinearLayout btnSave = view.findViewById(R.id.btnSave);
        ConstraintLayout mainScreen = view.findViewById(R.id.activity_main);
        LinearLayout loginScreen = view.findViewById(R.id.activity_login);
        LinearLayout profileScreen = view.findViewById(R.id.activity_profile);
        LinearLayout editProfile2Screen = view.findViewById(R.id.activity_editPro2);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent_editPassword2Profile = new Intent(getActivity(), profileScreen.getClass());
                startActivity(myIntent_editPassword2Profile);
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
                Intent myIntent_EditPassword2EditProfile = new Intent(getActivity(), editProfile2Screen.getClass());
                startActivity(myIntent_EditPassword2EditProfile);
            }
        });
    }
}
