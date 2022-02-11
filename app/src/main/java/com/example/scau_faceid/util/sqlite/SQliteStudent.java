package com.example.scau_faceid.util.sqlite;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//本地SQlite数据库，用于存储已考勤学生
@Entity
public class SQliteStudent {
    private String name;

    @PrimaryKey @NonNull
    private String account;//主键唯一,避免重复添加到考勤表

    private Boolean sex;
    private String major;
    private String phone;
    private String email;
    private String attendanceTime;

    public SQliteStudent(String name, String account, Boolean sex, String major, String phone, String email, String attendanceTime) {
        this.name = name;
        this.account = account;
        this.sex = sex;
        this.major = major;
        this.phone = phone;
        this.email = email;
        this.attendanceTime = attendanceTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAttendanceTime() {
        return attendanceTime;
    }

    public void setAttendanceTime(String attendanceTime) {
        this.attendanceTime = attendanceTime;
    }
}
