package com.stylefeng.guns.rest.modular.film.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.film.FilmApi;
import com.stylefeng.guns.api.film.vo.*;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.rest.common.persistence.dao.*;
import com.stylefeng.guns.rest.common.persistence.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liutj on 2019/3/14.
 */
@Component
@Service(interfaceClass = FilmApi.class)
public class DefaultFilmServiceImpl implements FilmApi{

    @Autowired
    private MoocBannerTMapper moocBannerTMapper;

    @Autowired
    private MoocFilmTMapper moocFilmTMapper;

    @Autowired
    private MoocCatDictTMapper moocCatDictTMapper;

    @Autowired
    private MoocYearDictTMapper moocYearDictTMapper;

    @Autowired
    private MoocSourceDictTMapper moocSourceDictTMapper;

    @Autowired
    private MoocFilmInfoTMapper moocFilmInfoTMapper;

    @Autowired
    private MoocActorTMapper moocActorTMapper;

    @Override
    public List<BannerVO> getBanners() {
        List<BannerVO> bannerVOS = new ArrayList<>();
        List<MoocBannerT> moocBannerTs = moocBannerTMapper.selectList(null);
        for(MoocBannerT moocBannerT : moocBannerTs)
        {
            BannerVO bannerVO = new BannerVO();
            bannerVO.setBannerId(moocBannerT.getUuid()+"");
            bannerVO.setBannerAddress(moocBannerT.getBannerAddress());
            bannerVO.setBannerUrl(moocBannerT.getBannerUrl());
            bannerVOS.add(bannerVO);
        }

        return bannerVOS;
    }

    @Override
    public FilmVO getHotFilms(boolean isLimit, int nums,int nowPage,int sortId,int sourceId,int yearId,int catId) {
        FilmVO filmVO = new FilmVO();

        List<FilmInfo> filmInfos = new ArrayList<>();
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status",1);
        //判断是否是首页需要的内容
        if(isLimit){
            //如果是，则限制条数，限制内容为热映影片
            Page<MoocFilmT> page = new Page<>(1,nums);
            List<MoocFilmT> moocFilmTS = moocFilmTMapper.selectPage(page, entityWrapper);
            filmInfos = getFilmInfos(moocFilmTS);

            filmVO.setFilmNum(moocFilmTS.size());
        }else{
            //如果不是，则是列表页，同样需要限制内容为热映影片
            Page<MoocFilmT> page = null;
            //根据sortId的不同，来组装不同的page对象
            switch (sortId){
                case 1:
                    page = new Page<>(nowPage,nums,"film_box_office");//film_box_office
                    break;
                case 2:
                    page = new Page<>(nowPage,nums,"film_time");
                    break;
                case 3:
                    page = new Page<>(nowPage,nums,"film_score");
                    break;
                default:
                    page = new Page<>(nowPage,nums,"film_box_office");
            }
            //如果sourceId,yearId,catId不为99，则表示要按照对应的编号进行查询
            if(sourceId != 99){
                entityWrapper.eq("film_source",sourceId);
            }
            if(yearId != 99){
                entityWrapper.eq("film_date",yearId);
            }
            if(catId != 99){
                String catStr = "%#"+catId+"#%";
                entityWrapper.like("film_cats",catStr);
            }
            List<MoocFilmT> moocFilmTS = moocFilmTMapper.selectPage(page, entityWrapper);
            filmInfos = getFilmInfos(moocFilmTS);
            //需要总页数
            Integer totalCounts = moocFilmTMapper.selectCount(entityWrapper);
            int totalPages = totalCounts / nums + 1;
            filmVO.setTotalPage(totalPages);
            filmVO.setTotalNowPge(nowPage);
            filmVO.setFilmNum(moocFilmTS.size());

        }
        filmVO.setFilmInfo(filmInfos);

        return filmVO;
    }

    private List<FilmInfo> getFilmInfos(List<MoocFilmT> moocFilmTS){
        List<FilmInfo> filmInfos = new ArrayList<>();
        for(MoocFilmT moocFilmT : moocFilmTS){
            FilmInfo filmInfo = new FilmInfo();
            filmInfo.setFilmId(moocFilmT.getUuid()+"");
            filmInfo.setImgAddress(moocFilmT.getImgAddress());
            filmInfo.setFilmType(moocFilmT.getFilmType());
            filmInfo.setFilmScore(moocFilmT.getFilmScore());
            filmInfo.setFilmName(moocFilmT.getFilmName());
            filmInfo.setScore(moocFilmT.getFilmScore());
            filmInfo.setExpectNum(moocFilmT.getFilmPresalenum());
            filmInfo.setBoxNum(moocFilmT.getFilmBoxOffice());
            filmInfo.setShowTime(DateUtil.getDay(moocFilmT.getFilmTime()));

            filmInfos.add(filmInfo);
        }
        return filmInfos;
    }

    @Override
    public FilmVO getSoonFilms(boolean isLimit, int nums,int nowPage,int sortId,int sourceId,int yearId,int catId) {
        FilmVO filmVO = new FilmVO();

        List<FilmInfo> filmInfos = new ArrayList<>();
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status",2);
        //判断是否是首页需要的内容
        if(isLimit){
            //如果是，则限制条数，限制内容为即将上映影片
            Page<MoocFilmT> page = new Page<>(1,nums);
            List<MoocFilmT> moocFilmTS = moocFilmTMapper.selectPage(page, entityWrapper);
            filmInfos = getFilmInfos(moocFilmTS);

            filmVO.setFilmNum(moocFilmTS.size());
        }else{
            //如果不是，则是列表页，同样需要限制内容为即将上映影片
            Page<MoocFilmT> page = null;
            //根据sortId的不同，来组装不同的page对象
            switch (sortId){
                case 1:
                    page = new Page<>(nowPage,nums,"film_preSaleNum");
                    break;
                case 2:
                    page = new Page<>(nowPage,nums,"film_time");
                    break;
                case 3:
                    page = new Page<>(nowPage,nums,"film_preSaleNum");
                    break;
                default:
                    page = new Page<>(nowPage,nums,"film_preSaleNum");
                    break;
            }
            //如果sourceId,yearId,catId不为99 ，则表示要按照对应的编号进行查询
            if(sourceId != 99 ){
                entityWrapper.eq("film_source",sourceId);
            }
            if(yearId != 99){
                entityWrapper.eq("film_date",yearId);
            }
            if(catId != 99){
                String catStr = "%#"+catId+"#%";
                entityWrapper.like("film_cats",catStr);
            }
            List<MoocFilmT> moocFilmTS = moocFilmTMapper.selectPage(page, entityWrapper);
            filmInfos = getFilmInfos(moocFilmTS);
            //需要总页数
            Integer totalCounts = moocFilmTMapper.selectCount(entityWrapper);
            int totalPages = totalCounts / nums + 1;
            filmVO.setTotalPage(totalPages);
            filmVO.setTotalNowPge(nowPage);

            filmVO.setFilmNum(moocFilmTS.size());
        }
        filmVO.setFilmInfo(filmInfos);

        return filmVO;
    }

    @Override
    public FilmVO getClassicFilms(int nums,int nowPage,int sortId,int sourceId,int yearId,int catId){
        FilmVO filmVO = new FilmVO();
        List<FilmInfo> filmInfos = new ArrayList<>();
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status",3);
        Page<MoocFilmT> page = null;
        //根据sortId的不同，来组装不同的page对象
        switch (sortId){
            case 1:
                page = new Page<>(nowPage,nums,"film_box_office");
                break;
            case 2:
                page = new Page<>(nowPage,nums,"film_time");
                break;
            case 3:
                page = new Page<>(nowPage,nums,"film_score");
                break;
            default:
                page = new Page<>(nowPage,nums,"film_box_office");
                break;
        }
        //如果sourceId,yearId,catId不为99 ，则表示要按照对应的编号进行查询
        if(sourceId != 99 ){
            entityWrapper.eq("film_source",sourceId);
        }
        if(yearId != 99){
            entityWrapper.eq("film_date",yearId);
        }
        if(catId != 99){
            String catStr = "%#"+catId+"#%";
            entityWrapper.like("film_cats",catStr);
        }
        List<MoocFilmT> moocFilmTS = moocFilmTMapper.selectPage(page, entityWrapper);
        filmInfos = getFilmInfos(moocFilmTS);
        //需要总页数
        Integer totalCounts = moocFilmTMapper.selectCount(entityWrapper);
        int totalPages = totalCounts / nums + 1;
        filmVO.setTotalPage(totalPages);
        filmVO.setTotalNowPge(nowPage);

        filmVO.setFilmNum(moocFilmTS.size());
        filmVO.setFilmInfo(filmInfos);
        return filmVO;
    }

    @Override
    public List<FilmInfo> getBoxRanking() {
        List<FilmInfo> filmInfos = new ArrayList<>();
        //正在上映的 票房前10名
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status",1);
        //取前10条
        Page<MoocFilmT> page = new Page<>(1,10,"film_box_office");
        List<MoocFilmT> moocFilmTS = moocFilmTMapper.selectPage(page, entityWrapper);
        filmInfos = getFilmInfos(moocFilmTS);
        return filmInfos;
    }

    @Override
    public List<FilmInfo> getExpectRanking() {
        List<FilmInfo> filmInfos = new ArrayList<>();
        //正在上映的 预售前10名
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status",2);
        //取前10条
        Page<MoocFilmT> page = new Page<>(1,10,"film_preSaleNum");
        List<MoocFilmT> moocFilmTS = moocFilmTMapper.selectPage(page, entityWrapper);
        filmInfos = getFilmInfos(moocFilmTS);
        return filmInfos;
    }

    @Override
    public List<FilmInfo> getTop() {
        List<FilmInfo> filmInfos = new ArrayList<>();
        //正在上映的 评分前10名
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status",1);
        //取前10条
        Page<MoocFilmT> page = new Page<>(1,10,"film_score");
        List<MoocFilmT> moocFilmTS = moocFilmTMapper.selectPage(page, entityWrapper);
        filmInfos = getFilmInfos(moocFilmTS);
        return filmInfos;
    }

    @Override
    public List<CatVO> getCats() {

        List<MoocCatDictT> moocCatDictTS =  moocCatDictTMapper.selectList(null);

        List<CatVO> catVOS = new ArrayList<>();
        for(MoocCatDictT moocCatDictT : moocCatDictTS){
            CatVO catVO = new CatVO();
            catVO.setCatId(moocCatDictT.getUuid()+"");
            catVO.setCatName(moocCatDictT.getShowName());
            catVOS.add(catVO);
        }
        return catVOS;
    }

    @Override
    public List<SourceVO> getSources() {
        List<MoocSourceDictT> moocSourceDictTS =  moocSourceDictTMapper.selectList(null);

        List<SourceVO> sourceVOS = new ArrayList<>();
        for(MoocSourceDictT moocSourceDictT : moocSourceDictTS){
            SourceVO sourceVO = new SourceVO();
            sourceVO.setSourceId(moocSourceDictT.getUuid()+"");
            sourceVO.setSourceName(moocSourceDictT.getShowName());
            sourceVOS.add(sourceVO);
        }
        return sourceVOS;
    }

    @Override
    public List<YearVO> getYears() {
        List<MoocYearDictT> moocYearDictTS =  moocYearDictTMapper.selectList(null);

        List<YearVO> yearVOS = new ArrayList<>();
        for(MoocYearDictT moocYearDictT : moocYearDictTS){
            YearVO yearVO = new YearVO();
            yearVO.setYearId(moocYearDictT.getUuid()+"");
            yearVO.setYearName(moocYearDictT.getShowName());
            yearVOS.add(yearVO);
        }
        return yearVOS;
    }

    @Override
    public FilmDetailVO getFilmDetail(int searchType, String searchParam) {
        FilmDetailVO filmDetailVO = null;
        if(searchType == 1){
            filmDetailVO = moocFilmTMapper.getFilmDetailByName("%"+searchParam+"%");
        }else{
            filmDetailVO = moocFilmTMapper.getFilmDetailById(searchParam);
        }
        return filmDetailVO;
    }

    private MoocFilmInfoT getFilmInfo(String filmId){
        MoocFilmInfoT moocFilmInfoT = new MoocFilmInfoT();
        moocFilmInfoT.setFilmId(filmId);

        moocFilmInfoT = moocFilmInfoTMapper.selectOne(moocFilmInfoT);

        return moocFilmInfoT;
    }

    @Override
    public FilmDescVO getFilmDesc(String filmId) {
        MoocFilmInfoT moocFilmInfoT = getFilmInfo(filmId);

        FilmDescVO filmDescVO = new FilmDescVO();
        filmDescVO.setBiography(moocFilmInfoT.getBiography());
        filmDescVO.setFilmId(filmId);
        return filmDescVO;
    }

    @Override
    public ImgVO getImgs(String filmId) {
        MoocFilmInfoT moocFilmInfoT = getFilmInfo(filmId);
        String filmImgStr = moocFilmInfoT.getFilmImgs();

        String[] imgStrs = filmImgStr.split(",");
        ImgVO imgVO = new ImgVO();
        imgVO.setMainImg(imgStrs[0]);
        imgVO.setImg01(imgStrs[1]);
        imgVO.setImg02(imgStrs[2]);
        imgVO.setImg03(imgStrs[3]);
        imgVO.setImg04(imgStrs[4]);
        return imgVO;
    }

    @Override
    public ActorVO getDescInfo(String filmId) {
        MoocFilmInfoT moocFilmInfoT = getFilmInfo(filmId);

        Integer directorId = moocFilmInfoT.getDirectorId();
        MoocActorT director = moocActorTMapper.selectById(directorId);
        ActorVO actorVO = new ActorVO();
        actorVO.setDirectorName(director.getActorName());
        actorVO.setImgAdress(director.getActorImg());
        return actorVO;
    }

    @Override
    public List<ActorVO> getActors(String filmId) {
        List<ActorVO> actors = moocActorTMapper.getActors(filmId);
        return actors;
    }

}
