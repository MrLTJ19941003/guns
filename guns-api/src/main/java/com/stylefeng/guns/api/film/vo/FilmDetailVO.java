package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by liutj on 2019/3/20.
 */
@Data
public class FilmDetailVO implements Serializable{

    private String filmId;

    private String fileName;

    private String fileEnName;

    private String imgAddress;

    private String score;

    private String scoreName;

    private String totalBox;

    private String info01;

    private String info02;

    private String info03;

    private InfoRequestVO info04;
}
