package com.stylefeng.guns.api.cinema;

import com.stylefeng.guns.api.cinema.vo.CinemaQueryVO;
import com.stylefeng.guns.api.cinema.vo.CinemasVO;

import java.util.List;

/**
 * Created by liutj on 2019/3/22.
 */
public interface CinemaServiceApi {

    public CinemasVO getCinemas(CinemaQueryVO cinemaQueryVO);
}
