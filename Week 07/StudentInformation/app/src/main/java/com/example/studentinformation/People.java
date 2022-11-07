package com.example.studentinformation;

public class People {
    private String peopleID;
    private String name;
    private int classID;

    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    private float avgScore;
    private int thumbnailID;

    public People() {}

    public People(String peopleID, String name, int classID, float avgScore) {
        this.peopleID = peopleID;
        this.name = name;
        this.classID = classID;
        this.avgScore = avgScore;
    }

    public String getPeopleID() {
        return peopleID;
    }

    public void setPeopleID(String peopleID) {
        this.peopleID = peopleID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(float avgScore) {
        this.avgScore = avgScore;
    }

    public int getThumbnailID() {
        return thumbnailID;
    }

    public void setThumbnailID(int thumbnailID) {
        this.thumbnailID = thumbnailID;
    }
}
