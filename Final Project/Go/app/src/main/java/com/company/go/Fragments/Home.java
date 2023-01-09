package com.company.go.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.company.go.Activities.DetailCar;
import com.company.go.Activities.ListActivity;
import com.company.go.Activities.MainActivity;
import com.company.go.Models.Car;
import com.company.go.R;
import com.company.go.Utils.PicassoTrustAll;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {
    private FirebaseFirestore db;
    private FirebaseAuth auth;

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
        auth = FirebaseAuth.getInstance();
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

        TextView username = view.findViewById(R.id.user_name);
        ImageView avatar = view.findViewById(R.id.avatar);
        TextView viewList = view.findViewById(R.id.view_list);
        TextView viewList1 = view.findViewById(R.id.view_list1);

        PicassoTrustAll.getInstance(getActivity())
                .load(auth.getCurrentUser().getPhotoUrl())
                .fit().into(avatar);
        username.setText(auth.getCurrentUser().getDisplayName());

        viewList.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), ListActivity.class);
            getActivity().startActivity(intent);

            for (Fragment fragment : getActivity().getSupportFragmentManager().getFragments()) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .remove(fragment)
                        .commit();
            }
        });
        viewList1.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), ListActivity.class);
            getActivity().startActivity(intent);

            for (Fragment fragment : getActivity().getSupportFragmentManager().getFragments()) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .remove(fragment)
                        .commit();
            }
        });

        db.collection("cars")
                .orderBy("price")
                .limit(6)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (getContext() == null) return;

                        if (task.isSuccessful()) {
                            QuerySnapshot document = task.getResult();
                            List<Car> car = document.toObjects(Car.class);
                            if (task.isSuccessful()) {
                                Card card = Card.newInstance(car.get(0));
                                Card card1 = Card.newInstance(car.get(1));
                                Card card2 = Card.newInstance(car.get(2));
                                Card card3 = Card.newInstance(car.get(3));
                                Card card4 = Card.newInstance(car.get(4));
                                Card card5 = Card.newInstance(car.get(5));

                                FragmentTransaction ft = getChildFragmentManager().beginTransaction();

                                ft.replace(R.id.testCard, card);
                                ft.replace(R.id.testCard1, card1);
                                ft.replace(R.id.testCard2, card2);
                                ft.replace(R.id.testCard3, card3);
                                ft.replace(R.id.testCard4, card4);
                                ft.replace(R.id.testCard5, card5);

                                ft.commitAllowingStateLoss();
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