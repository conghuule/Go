package com.company.go.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.company.go.Models.Car;
import com.company.go.R;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.service.Common;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

public class NearMe extends Fragment implements OnMapReadyCallback {
    private FusedLocationProviderClient fusedLocationClient;
    private GoogleMap mMap;
    private Marker userMarker = null;
    BottomSheetBehavior mBottomSheetBehavior;

    FirebaseFirestore db;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_near_me, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());

        View bottomSheet = view.findViewById(R.id.bottom_sheet);
        ImageView myLocationBtn = view.findViewById(R.id.my_location_btn);

        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setPeekHeight(0);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        myLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUserLocation();
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        getUserLocation();

        LocationRequest locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000).build();
        if (checkPermission()) {
            return;
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);

                Location location = locationResult.getLastLocation();
                LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());
                if (userMarker == null) {
                    userMarker = addMarker(pos, "User", R.drawable.avatr, 100, 100);
                }

                userMarker.setPosition(pos);
            }

        }, Looper.myLooper());

        getData();

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                String id = (String) marker.getTag();

                if (id != null) {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                    db.collection("cars")
                            .document(id)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @SuppressLint("ResourceType")
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            Card card = Card.newInstance(new Car(document));
                                            getChildFragmentManager()
                                                    .beginTransaction()
                                                    .replace(R.id.card_data, card)
                                                    .commit();
                                            Log.d("Marker Cliked", id);
                                        } else {
                                            Log.d("Error", "No such document");
                                        }
                                    } else {
                                        Log.d("Error", "get failed with ", task.getException());
                                    }
                                }
                            });
                }

                return false;
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        fusedLocationClient.removeLocationUpdates(new LocationCallback() {
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();


        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null)
            getChildFragmentManager().beginTransaction().remove(mapFragment).commitAllowingStateLoss();
    }

    private void getUserLocation() {
        if (checkPermission()) {
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());
                            CameraPosition.Builder positionBuilder = new CameraPosition.Builder();
                            positionBuilder.target(pos);
                            positionBuilder.zoom(15f);
                            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(positionBuilder.build()));
                        }
                    }
                });
    }

    private Boolean checkPermission() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return true;
        }

        return false;
    }

    private Marker addMarker(LatLng pos, String title, @DrawableRes int icon, int width, int height) {
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(), icon);
        return mMap.addMarker(new MarkerOptions()
                .position(pos)
                .title(title)
                .icon(BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(imageBitmap, width, height, false))));
    }

    private void getData() {
        db.collection("cars")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                GeoPoint address = (GeoPoint) document.getGeoPoint("address");
                                int id = document.getLong("id").intValue();
                                LatLng pos = new LatLng(address.getLatitude(), address.getLongitude());

                                Marker carMarker = addMarker(pos, String.valueOf(id), R.drawable.bmw_logo, 100, 100);
                                carMarker.setTag(document.getId());
                            }
                        } else {
                            Log.d("cars data", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}