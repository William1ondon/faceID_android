package com.example.scau_faceid.info;

import java.io.Serializable;

public class AccountTeacher implements Serializable {
    private String account;
    private String password;

    public AccountTeacher(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
