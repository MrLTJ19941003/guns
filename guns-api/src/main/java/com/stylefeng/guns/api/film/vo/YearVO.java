package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by liutj on 2019/3/15.
 */
@Data
public class YearVO implements Serializable{

    private String yearId;

    private String yearName;

    private boolean isActive;

}
