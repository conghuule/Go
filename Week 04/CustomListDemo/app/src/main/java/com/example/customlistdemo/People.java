package com.example.customlistdemo;

class People {
    Integer avatar;
    String name;
    String phone;

    public People(Integer avatar, String name, String phone ) {
        this.avatar = avatar;
        this.name = name;
        this.phone  = phone;
    }

    Integer getAvatar() {
        return avatar;
    }

    String getPhone() {
        return phone;
    }

    String getName() {
        return name;
    }

}