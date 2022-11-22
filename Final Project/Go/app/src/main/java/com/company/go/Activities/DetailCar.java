package com.company.go.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.company.go.Models.Car;
import com.company.go.R;
import com.company.go.Utils.Helper;
import com.company.go.Utils.PicassoTrustAll;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetailCar extends AppCompatActivity {
    private Car car;
    private FirebaseFirestore db;
    private String listview_array[]={ "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE", "TEN" };

    TextView address, model, brand, price, type, capacity, fuelType, color;
    ImageView imageView, certImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
        setContentView(R.layout.activity_detail_car);

        db = FirebaseFirestore.getInstance();

        Bundle extras = getIntent().getExtras();
        String id = "";
        if (extras != null) {
            id = extras.getString("id");
        }

        ListView reviewList = findViewById(R.id.review_list);
        reviewList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listview_array));
        Helper.getListViewSize(reviewList);

        address = findViewById(R.id.address);
        model = findViewById(R.id.model);
        brand = findViewById(R.id.brand);
        price = findViewById(R.id.price);
        type = findViewById(R.id.type);
        capacity = findViewById(R.id.capacity);
        fuelType = findViewById(R.id.fuel_type);
        color = findViewById(R.id.color);
        imageView = findViewById(R.id.car_image);
        certImg = findViewById(R.id.certificate_img);

        db.collection("cars")
                .document(id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                car = document.toObject(Car.class);
                                Log.d("hihihi", car.getRegistration_certificate().get("front_image").toString());
                                setData();
                            } else {
                                car = null;
                                Log.d("Error", "No such document");
                            }
                        } else {
                            Log.d("Error", "get failed with ", task.getException());
                        }
                    }
                });
    }


    private void setData() {
        if (car != null) {
            address.setText(car.getLocation().get("name").toString());
            price.setText(car.getPriceFormat());
            type.setText(car.getInformation().get("type").toString());
            model.setText(car.getInformation().get("model").toString());
            brand.setText(car.getInformation().get("brand").toString());
            capacity.setText(car.getInformation().get("capacity").toString() + " Seats");
            fuelType.setText(car.getInformation().get("fuel_type").toString());
            color.setText(car.getInformation().get("color").toString());

            Log.d("PICASSO", car.getRegistration_certificate().get("front_image").toString());

            PicassoTrustAll.getInstance(getBaseContext())
                    .load(car.getRegistration_certificate().get("front_image").toString())
                    .fit().into(certImg);

            PicassoTrustAll.getInstance(getBaseContext())
                    .load(car.getAvatar())
                    .fit().into(imageView);
        }
    }

}