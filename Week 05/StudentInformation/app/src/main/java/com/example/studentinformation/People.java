package com.example.studentinformation;

class People {
    Integer avatar;
    String id;
    String name;
    String class_name;
    Float avg_point;

    public People(Integer avatar, String id, String name, String class_name, Float avg_point) {
        this.avatar = avatar;
        this.id = id;
        this.name = name;
        this.class_name = class_name;
        this.avg_point = avg_point;
    }

    Integer getAvatar() {
        return avatar;
    }

    String getId() {
        return id;
    }

    String getName() {
        return name;
    }

    String getClass_name() {
        return class_name;
    }

    Float getAvg_point() {
        return avg_point;
    }

}