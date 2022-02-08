package com.example.scau_faceid.util.email;

import static java.lang.String.valueOf;

public class CreateCode {
    private String code;

    public CreateCode(){
        createCode();
    }


    public String getCode() {
        return code;
    }
    private void createCode(){
        code=valueOf((int)(Math.random()*1000000));
    }
}
