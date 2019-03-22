package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by liutj on 2019/3/20.
 */
@Data
public class ActorVO implements Serializable{
    private String imgAdress;
    private String directorName;
    private String roleName;
}
