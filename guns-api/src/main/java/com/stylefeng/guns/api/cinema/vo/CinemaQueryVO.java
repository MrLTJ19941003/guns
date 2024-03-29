package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by liutj on 2019/3/22.
 */
@Data
public class CinemaQueryVO implements Serializable{

    private Integer brandId = 99;
    private Integer hallType = 99;
    private Integer districtId = 99;
    private Integer pageSize = 12;
    private Integer nowPage = 1;


}
