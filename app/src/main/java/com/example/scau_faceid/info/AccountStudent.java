package com.example.scau_faceid.info;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class AccountStudent implements Serializable {
    /*
        什么是Serializable接口
        一个对象序列化的接口，一个类只有实现了Serializable接口，它的对象才能被序列化

        序列化是将对象状态转换为可保持或传输的格式的过程。与序列化相对的是反序列化，它将流转换为对象。这两个过程结合起来，可以轻松地存储和传输数据

        Serializable接口就是Java提供用来进行高效率的异地共享实例对象的机制，实现这个接口即可。

        如果我们没有自己声明一个serialVersionUID变量,接口会默认生成一个serialVersionUID
        但是强烈建议用户自定义一个serialVersionUID,因为默认的serialVersinUID对于class的细节非常敏感，反序列化时可能会导致InvalidClassException这个异常。
     */
    private String name;
    private String account;
    private String password;
    private Boolean sex;
    private String major;
    private String phone;
    private String email;
    private byte[] FaceFeatureData;

    public AccountStudent(String name, String account, String password, Boolean sex, String major, String phone, String email, byte[] FaceFeatureData) {
        this.name = name;
        this.account = account;
        this.password = password;
        this.sex = sex;
        this.major = major;
        this.phone = phone;
        this.email = email;
        this.FaceFeatureData = FaceFeatureData;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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


    public byte[] getFaceFeatureData() {
        return FaceFeatureData;
    }

    public void setFaceFeatureData(byte[] faceFeatureData) {
        FaceFeatureData = faceFeatureData;
    }
}
