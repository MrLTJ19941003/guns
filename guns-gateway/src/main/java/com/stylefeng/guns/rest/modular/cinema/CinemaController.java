package com.stylefeng.guns.rest.modular.cinema;

import com.stylefeng.guns.api.cinema.vo.CinemaQueryVO;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by liutj on 2019/3/22.
 */
@RestController
@RequestMapping("/cinema/")
public class CinemaController {

    @RequestMapping("")
    public ResponseVO getCinema(){

        return null;
    }
}
