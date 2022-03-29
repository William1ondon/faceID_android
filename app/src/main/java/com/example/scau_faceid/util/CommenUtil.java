package com.example.scau_faceid.util;

import android.os.StrictMode;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommenUtil {

    /**
     * 验证手机号码
     * @param mobiles
     * @return
     */
    public static boolean isPhone(String mobiles){
        boolean flag = false;
        try{
            String pattern = "^((13[0-9])|(14[5,7,9])|(15[0-3,5-9])|(166)|(17[3,5,6,7,8])" + "|(18[0-9])|(19[8,9]))\\d{8}$";
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(mobiles);
            flag = m.matches();
        }catch(Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 验证邮箱地址是否正确
     * @param email
     * @return
     */
    public static boolean IsEmail(String email){
        boolean flag = false;
        try{
            String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        }catch(Exception e){
            e.printStackTrace();
        }
        return flag;
    }
}
