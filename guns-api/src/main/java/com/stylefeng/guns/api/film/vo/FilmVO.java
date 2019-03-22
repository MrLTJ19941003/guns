package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liutj on 2019/3/14.
 */
@Data
public class FilmVO implements Serializable {

    private int filmNum;

    private int totalPage;

    private int totalNowPge;

    private List<FilmInfo> filmInfo;
}
