package com.company.go.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.company.go.Activities.MainActivity;
import com.company.go.Models.Car;
import com.company.go.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {
    private FirebaseFirestore db;

    public static Home newInstance() {
        Home fragment = new Home();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();

        ((MainActivity) requireActivity()).setActiveTab(R.id.home);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
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
                        if (getContext() == null) return;

                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            Car car = document.toObject(Car.class);
                            if (document.exists()) {
                                Card card = Card.newInstance(car);
                                getChildFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.testCard, card)
                                        .commitAllowingStateLoss();
                            } else {
                                Log.d("Error", "No such document");
                            }
                        } else {
                            Log.d("Error", "get failed with ", task.getException());
                        }
                    }
                });
    }
}