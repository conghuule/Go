package com.company.go.Models;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Car implements Serializable {
    private String avatar;
    private HashMap<String, Object> location;
    private String id;
    private HashMap<String, Object> information;
    private Integer owner_id;
    private String plate_number;
    private Integer price;
    private HashMap<String, Object> registration_certificate;
    private String status;
    private List<HashMap<String, Object>> reviews;

    public Car() {}

    public HashMap<String, Object> getLocation() {
        return location;
    }

    public HashMap<String, Object> getInformation() {
        return information;
    }

    public HashMap<String, Object> getRegistration_certificate() {
        return registration_certificate;
    }

    public String getId() {
        return id;
    }

    public Integer getOwner_id() {
        return owner_id;
    }

    public Integer getPrice() {
        return price;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getPlate_number() {
        return plate_number;
    }

    public String getStatus() {
        return status;
    }

    public String getPriceFormat() {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();

        symbols.setGroupingSeparator('.');
        formatter.setDecimalFormatSymbols(symbols);

        return formatter.format(price.longValue()) + " đ";
    }

    public List<HashMap <String, Object>> getReviews() {
        return reviews;
    }
}
