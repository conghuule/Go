package com.company.go.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.company.go.Adapters.ReviewAdapter;
import com.company.go.Fragments.InputLabel;
import com.company.go.Models.Car;
import com.company.go.Models.Review;
import com.company.go.R;
import com.company.go.Utils.Helper;
import com.company.go.Utils.PicassoTrustAll;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DetailCar extends AppCompatActivity {
    private Car car;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private List<Review> reviewListData;

    TextView address, model, brand, price, type, capacity, fuelType, color;
    ImageView imageView;
    ListView reviewList;
    Button submitButton;
    InputLabel reviewInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
        setContentView(R.layout.activity_detail_car);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        Bundle extras = getIntent().getExtras();
        String id = "";
        if (extras != null) {
            id = extras.getString("id");
        }

        reviewListData = new ArrayList<>();
        reviewList = findViewById(R.id.review_list);
        address = findViewById(R.id.address);
        model = findViewById(R.id.model);
        brand = findViewById(R.id.brand);
        price = findViewById(R.id.price);
        type = findViewById(R.id.type);
        capacity = findViewById(R.id.capacity);
        fuelType = findViewById(R.id.fuel_type);
        color = findViewById(R.id.color);
        imageView = findViewById(R.id.car_image);
        reviewInput = (InputLabel) getSupportFragmentManager().findFragmentById(R.id.review_input);
        submitButton = findViewById(R.id.submit_button);

        String finalId = id;
        submitButton.setOnClickListener(view -> {
            List<HashMap<String, Object>> reviewList = car.getReviews();

            HashMap<String, Object> newReview = new HashMap<>();
            newReview.put("customer_id",auth.getCurrentUser().getUid());
            newReview.put("review",reviewInput.getInputValue());
            newReview.put("time", Timestamp.now());
            reviewList.add((newReview));

            db.collection("cars").document(finalId)
                    .update("reviews", reviewList);
        });

        db.collection("cars")
                .document(id)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot snapshot,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("Error", "Listen failed.", e);
                            return;
                        }

                        if (snapshot != null && snapshot.exists()) {
                            car = snapshot.toObject(Car.class);
                            setData();
                        } else {
                            car = null;
                            Log.d("Error", "No such document");
                        }
                    }
                });
    }


    private void setData() {
        if (car != null) {
            reviewListData.clear();
            address.setText(car.getLocation().get("name").toString());
            price.setText(car.getPriceFormat());
            type.setText(car.getInformation().get("type").toString());
            model.setText(car.getInformation().get("model").toString());
            brand.setText(car.getInformation().get("brand").toString());
            capacity.setText(car.getInformation().get("capacity").toString() + " Seats");
            fuelType.setText(car.getInformation().get("fuel_type").toString());
            color.setText(car.getInformation().get("color").toString());

            PicassoTrustAll.getInstance(getBaseContext())
                    .load(car.getAvatar())
                    .fit().into(imageView);

            if (car.getReviews() == null) {
                return;
            }
            for (HashMap<String, Object> reviewData : car.getReviews()) {
                String userID = (String) reviewData.get("customer_id");

                db.collection("customers").document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                HashMap<String, Object> profile = (HashMap<String, Object>) document.get("profile");
                                String userName = (String) profile.get("username");
                                String userAvatar = (String) profile.get("profile_image");

                                Review review = new Review(userName, userAvatar, (String) reviewData.get("review"));

                                reviewListData.add(review);
                                ReviewAdapter reviewAdapter = new ReviewAdapter(DetailCar.this, R.layout.adapter_review, reviewListData);
                                reviewList.setAdapter(reviewAdapter);
                                Helper.getListViewSize(reviewList);
                                Log.d("hihi", String.valueOf(reviewListData.size()));
                            } else {
                                Log.d("TAG", "No such document");
                            }
                        } else {
                            Log.d("TAG", "get failed with ", task.getException());
                        }
                    }
                });
            }

            ReviewAdapter reviewAdapter = new ReviewAdapter(this, R.layout.adapter_review, reviewListData);
            reviewList.setAdapter(reviewAdapter);
            Helper.getListViewSize(reviewList);
        }
    }

}