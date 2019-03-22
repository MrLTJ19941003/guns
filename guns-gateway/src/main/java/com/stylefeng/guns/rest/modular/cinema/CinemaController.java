package com.stylefeng.guns.rest.modular.cinema;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.cinema.CinemaServiceApi;
import com.stylefeng.guns.api.cinema.vo.CinemaQueryVO;
import com.stylefeng.guns.api.cinema.vo.CinemasInfoVO;
import com.stylefeng.guns.api.cinema.vo.CinemasVO;
import com.stylefeng.guns.rest.modular.cinema.vo.CinemasListVO;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by liutj on 2019/3/22.
 */
@RestController
@RequestMapping("/cinema/")
public class CinemaController {

    @Reference(interfaceClass = CinemaServiceApi.class)
    private CinemaServiceApi cinemaServiceApi;

    @RequestMapping("getCinemas")
    public ResponseVO getCinema(CinemaQueryVO cinemaQueryVO){

        CinemasVO cinemasVO = cinemaServiceApi.getCinemas(cinemaQueryVO);
        CinemasListVO cinemasListVO = new CinemasListVO();
        cinemasListVO.setCinemas(cinemasVO.getCinemas());
        return ResponseVO.success(cinemasListVO,cinemasVO.getNowPage(),cinemasVO.getTotalPage());
    }
}
