package com.stylefeng.guns.api.user.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by liutj on 2019/3/5.
 */
@Data
public class UserModel implements Serializable{

    private String username;
    private String password;
    private String email;
    private String phone;
    private String address;
    private String sex;
}
