package com.company.go.Models;

import com.google.firebase.Timestamp;

public class Review {
    private String userName;
    private String userAvatar;
    private String review;
    private Timestamp time;

    public Review(String userName, String userAvatar, String review) {
        this.userAvatar = userAvatar;
        this.userName = userName;
        this.review = review;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public String getReview() {
        return review;
    }

    public String getUserName() {
        return userName;
    }

    public Timestamp getTime() {
        return time;
    }
}
