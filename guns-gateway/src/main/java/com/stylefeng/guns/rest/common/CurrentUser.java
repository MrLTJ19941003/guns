package com.stylefeng.guns.rest.common;

/**
 * Created by liutj on 2019/3/5.
 */
public class CurrentUser {

    //线程绑定的存储空间
    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void saveUserID(String userID){
        threadLocal.set(userID);
    }

    public static String getUserID(){
        return threadLocal.get();
    }
}
