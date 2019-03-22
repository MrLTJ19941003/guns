package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liutj on 2019/3/22.
 */
@Data
public class CinemasVO implements Serializable{
    private int nowPage;

    private int totalPage;

    private List<CinemasInfoVO> cinemas;
}
