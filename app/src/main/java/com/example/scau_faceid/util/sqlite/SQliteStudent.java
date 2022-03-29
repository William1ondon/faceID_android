package com.example.scau_faceid.util.sqlite;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//本地SQlite数据库，用于存储已考勤学生
@Entity
public class SQliteStudent {
    private String name;

    @PrimaryKey
    @NonNull
    private String account;//主键唯一,避免重复添加到考勤表

    private String attendanceTime;

    public SQliteStudent(String name, String account, String attendanceTime) {
        this.name = name;
        this.account = account;
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

    public String getAttendanceTime() {
        return attendanceTime;
    }

    public void setAttendanceTime(String attendanceTime) {
        this.attendanceTime = attendanceTime;
    }
}
