package com.stylefeng.guns.rest.modular.cinema.vo;

import com.stylefeng.guns.api.cinema.vo.CinemasInfoVO;
import lombok.Data;

import java.util.List;

/**
 * Created by liutj on 2019/3/22.
 */
@Data
public class CinemasListVO {
    private List<CinemasInfoVO> cinemas;
}
