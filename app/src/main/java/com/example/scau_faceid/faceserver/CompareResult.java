package com.example.scau_faceid.faceserver;


import com.example.scau_faceid.info.AccountStudent;

public class CompareResult {
    private String userName;
    private float similar;
    private int trackId;
    private AccountStudent student;

    public CompareResult(String userName, float similar, AccountStudent student) {
        this.userName = userName;
        this.similar = similar;
        this.student = student;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public float getSimilar() {
        return similar;
    }

    public void setSimilar(float similar) {
        this.similar = similar;
    }

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    public AccountStudent getStudent() {
        return student;
    }

    public void setStudent(AccountStudent student) {
        this.student = student;
    }
}
