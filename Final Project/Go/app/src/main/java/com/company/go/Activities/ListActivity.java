package com.company.go.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.company.go.Adapters.CardAdapter;
import com.company.go.Models.Car;
import com.company.go.R;
import com.company.go.Utils.Helper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        db = FirebaseFirestore.getInstance();
        ListView listView = findViewById(R.id.list_item);

        db.collection("cars").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot document = task.getResult();
                    List<Car> carList = document.toObjects(Car.class);

                    CardAdapter adapter = new CardAdapter(ListActivity.this, R.layout.fragment_card, carList);
                    listView.setAdapter(adapter);
                    Helper.getListViewSize(listView);
                }
            }
        });
    }
}