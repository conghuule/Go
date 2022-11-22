package com.company.go.Models;

import java.util.HashMap;

public class User {
    private Integer id;
    private HashMap<String, Object> driver_lisence;
    private HashMap<String, Object> profile;

    public User() {}

    public Integer getId() {
        return id;
    }

    public HashMap<String, Object> getDriver_lisence() {
        return driver_lisence;
    }

    public HashMap<String, Object> getProfile() {
        return profile;
    }
}
