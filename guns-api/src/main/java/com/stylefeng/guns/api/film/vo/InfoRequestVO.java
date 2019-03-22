package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by liutj on 2019/3/21.
 */
@Data
public class InfoRequestVO implements Serializable{

    private String biography;
    private ActorRequestVO actors;
    private ImgVO imgs;
    private String filmId;

}
