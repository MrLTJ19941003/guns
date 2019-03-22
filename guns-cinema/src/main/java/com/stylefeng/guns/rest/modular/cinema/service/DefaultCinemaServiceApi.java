package com.stylefeng.guns.rest.modular.cinema.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.cinema.CinemaServiceApi;
import com.stylefeng.guns.api.cinema.vo.CinemaQueryVO;
import com.stylefeng.guns.api.cinema.vo.CinemasInfoVO;
import com.stylefeng.guns.api.cinema.vo.CinemasVO;
import com.stylefeng.guns.rest.common.persistence.dao.MoocCinemaTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MoocCinemaT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liutj on 2019/3/22.
 */
@Component
@Service(interfaceClass = CinemaServiceApi.class)
public class DefaultCinemaServiceApi implements CinemaServiceApi{

    @Autowired
    private MoocCinemaTMapper moocCinemaTMapper;

    @Override
    public CinemasVO getCinemas(CinemaQueryVO cinemaQueryVO) {
        CinemasVO cinemasVO = new CinemasVO();
        List<CinemasInfoVO> cinemasVOList = null;
        EntityWrapper<MoocCinemaT> entityWrapper = new EntityWrapper<>();
        if(cinemaQueryVO.getBrandId() != 99){
            entityWrapper.eq("brand_id",cinemaQueryVO.getBrandId());
        }
        if(cinemaQueryVO.getHallType() != 99){
            entityWrapper.like("hall_ids","%#"+cinemaQueryVO.getHallType()+"#%");

        }
        if(cinemaQueryVO.getDistrictId() != 99){
            entityWrapper.eq("area_id",cinemaQueryVO.getDistrictId());
        }

        Page<MoocCinemaT> page = new Page(cinemaQueryVO.getNowPage(),cinemaQueryVO.getPageSize());
        List<MoocCinemaT> moocCinemaTS = moocCinemaTMapper.selectPage(page, entityWrapper);

        cinemasVOList = getCinemasVO(moocCinemaTS);
        cinemasVO.setNowPage(cinemaQueryVO.getNowPage());
        cinemasVO.setCinemas(cinemasVOList);
        Integer cinemaCount = moocCinemaTMapper.selectCount(entityWrapper);
        int totalPage = cinemaCount / cinemaQueryVO.getPageSize() + 1;
        cinemasVO.setTotalPage(totalPage);
        return cinemasVO;
    }

    private List<CinemasInfoVO> getCinemasVO(List<MoocCinemaT> moocCinemaTList){
        List<CinemasInfoVO> cinemasVOList = new ArrayList<>();
        for (MoocCinemaT moocCinemaT:moocCinemaTList) {
            CinemasInfoVO cinemasVO = new CinemasInfoVO();
            cinemasVO.setUuid(moocCinemaT.getUuid()+"");
            cinemasVO.setCinemaName(moocCinemaT.getCinemaName());
            cinemasVO.setAddress(moocCinemaT.getCinemaAddress());
            cinemasVO.setMinimumPrice(moocCinemaT.getMinimumPrice());
            cinemasVOList.add(cinemasVO);
        }
        return cinemasVOList;
    }

}
