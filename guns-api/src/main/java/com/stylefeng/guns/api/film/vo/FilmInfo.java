package com.stylefeng.guns.api.film.vo;


import lombok.Data;

import java.io.Serializable;

/**
 * Created by liutj on 2019/3/14.
 */
@Data
public class FilmInfo implements Serializable {

    private String filmId;
    private int filmType;
    private String imgAddress;
    private String filmName;
    private String filmScore;
    private int  expectNum;
    private String  showTime;
    private int boxNum;
    private String score;

}
