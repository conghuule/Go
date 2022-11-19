package com.company.go.Models;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.GeoPoint;

import java.io.Serializable;
import java.util.HashMap;

public class Car implements Serializable {
    private String image;
    private GeoPoint address;
    private Integer id;
    private HashMap<String, Object> information;
    private Integer owner_id;
    private String plate_number;
    private Integer price;
    private HashMap<String, Object> registration_certificate;
    private String status;

    public Car(DocumentSnapshot document) {
        image = document.getString("avatar");
        address = document.getGeoPoint("address");
        id = document.getLong("id").intValue();
        information = (HashMap<String, Object>) document.getData().get("information");
        owner_id = document.getLong("owner_id").intValue();
        plate_number= document.getString("plate_number");
        price = document.getLong("price").intValue();
        registration_certificate = (HashMap<String, Object>) document.getData().get("registration_certificate");
    }

    public GeoPoint getAddress() {
        return address;
    }

    public HashMap<String, Object> getInformation() {
        return information;
    }

    public HashMap<String, Object> getRegistration_certificate() {
        return registration_certificate;
    }

    public Integer getId() {
        return id;
    }

    public Integer getOwner_id() {
        return owner_id;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public String getPlate_number() {
        return plate_number;
    }

    public String getStatus() {
        return status;
    }
}
