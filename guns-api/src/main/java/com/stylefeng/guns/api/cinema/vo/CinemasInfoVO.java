package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liutj on 2019/3/22.
 */
@Data
public class CinemasInfoVO implements Serializable{

    private String uuid;
    private String cinemaName;
    private String address;
    private double minimumPrice;


}
