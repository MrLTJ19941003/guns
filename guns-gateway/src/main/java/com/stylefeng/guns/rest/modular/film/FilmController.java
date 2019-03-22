package com.stylefeng.guns.rest.modular.film;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcContext;
import com.stylefeng.guns.api.film.FilmApi;
import com.stylefeng.guns.api.film.FilmAsyncApi;
import com.stylefeng.guns.api.film.vo.*;
import com.stylefeng.guns.rest.modular.film.vo.FilmConditionVO;
import com.stylefeng.guns.rest.modular.film.vo.FilmIndexVO;
import com.stylefeng.guns.rest.modular.film.vo.FilmRequestVO;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by liutj on 2019/3/6.
 */

@RestController
@RequestMapping("/film/")
public class FilmController {

    private static final String IMG_PRE = "www.jiangliuer.club";

    @Reference(interfaceClass = FilmApi.class,check = false)
    private FilmApi filmApi;

    @Reference(interfaceClass = FilmAsyncApi.class,async = true,check = false)
    private FilmAsyncApi filmAsyncApi;


    //获取首页信息接口

    /**
     *      API网关：
     *          1、接口功能聚合【API聚合】
     *          好处：
     *              1、6个接口，一次请求，同一时刻节省了五次HTTP请求
     *              2、同一个接口对外暴露，降低了前后端分离开发的难度和复杂度
     *          坏处：
     *              1、一次获取数据过多，容易出现网络问题
     * @return ResponseVO
     */
    @RequestMapping(value="getIndex",method = RequestMethod.GET)
    public ResponseVO getIndex(){
        FilmIndexVO filmIndexVO = new FilmIndexVO();
        //获取banner信息
        List<BannerVO> banners = filmApi.getBanners();
        //获取正在热映的电影
        FilmVO hotFilms = filmApi.getHotFilms(true, 8,1,1,99,99,99);
        //即将上映的电源
        FilmVO soonFilms = filmApi.getSoonFilms(true, 8,1,1,99,99,99);
        //票房排行榜
        List<FilmInfo> boxRanking = filmApi.getBoxRanking();
        //获取受欢迎的榜单
        List<FilmInfo> expectRanking = filmApi.getExpectRanking();
        //获取前一百
        List<FilmInfo> top = filmApi.getTop();
        //组装返回的VO
        filmIndexVO.setBanners(banners);
        filmIndexVO.setHotFilms(hotFilms);
        filmIndexVO.setSoonFilms(soonFilms);
        filmIndexVO.setBoxRanking(boxRanking);
        filmIndexVO.setExpectRanking(expectRanking);
        filmIndexVO.setTop100(top);

        return ResponseVO.success(filmIndexVO,IMG_PRE);
    }

    /**
     * 影片条件列表查询接口
     * @return
     */
    @RequestMapping(value="getConditionList",method = RequestMethod.GET)
    public ResponseVO getConditionList(@RequestParam(name="catId",required = false,defaultValue = "99") String catId,
                                       @RequestParam(name="sourceId",required = false,defaultValue = "99") String sourceId,
                                       @RequestParam(name="yearId",required = false,defaultValue = "99") String yearId){
        FilmConditionVO filmConditionVO = new FilmConditionVO();

        //标示位
        boolean flag = false;
        //类型集合
        List<CatVO> cats = filmApi.getCats();
        List<CatVO> catResult = new ArrayList<>();
        CatVO cat = null;

        for(CatVO catVO : cats){
            if(catVO.getCatId().equals("99")){
                cat = catVO;
                cat.setActive(true);
                continue;
            }
            //判断集合是否存在catId
            if(catVO.getCatId().equals(catId)){
                 //如果存在，则将对应的实体变成active状态
                flag = true;
                catVO.setActive(true);
            }
            catResult.add(catVO);

        }
        if(flag){
            //如果不存在，则将全部变成active状态
            cat.setActive(false);
            catResult.add(cat);
        }else{
            catResult.add(cat);
        }
        filmConditionVO.setCatInfo(catResult);
        //片源集合
        flag = false;
        List<SourceVO> sources = filmApi.getSources();
        List<SourceVO> sourceResult = new ArrayList<>();
        SourceVO sourceVO = null;

        for(SourceVO source : sources){
            if(source.getSourceId().equals("99")){
                sourceVO = source;
                sourceVO.setActive(true);
                continue;
            }
            //判断集合是否存在catId
            if(source.getSourceId().equals(sourceId)){
                //如果存在，则将对应的实体变成active状态
                flag = true;
                source.setActive(true);
            }
            sourceResult.add(source);

        }
        if(flag){
            //如果不存在，则将全部变成active状态
            sourceVO.setActive(false);
            sourceResult.add(sourceVO);
        }else{
            sourceResult.add(sourceVO);
        }
        filmConditionVO.setSourceInfo(sourceResult);
        //年份集合
        flag = false;
        List<YearVO> years = filmApi.getYears();
        List<YearVO> yearResult = new ArrayList<>();
        YearVO yearVO = null;

        for(YearVO year : years){
            if(year.getYearId().equals("99")){
                yearVO = year;
                yearVO.setActive(true);
                continue;
            }
            //判断集合是否存在catId
            if(year.getYearId().equals(yearId)){
                //如果存在，则将对应的实体变成active状态
                flag = true;
                year.setActive(true);
            }
            yearResult.add(year);

        }
        if(flag){
            //如果不存在，则将全部变成active状态
            yearVO.setActive(false);
            yearResult.add(yearVO);
        }else{
            yearResult.add(yearVO);
        }
        filmConditionVO.setYearInfo(yearResult);

        return ResponseVO.success(filmConditionVO);
    }

    /**
     * 影片条件列表查询接口
     * @return
     */
    @RequestMapping(value="getFilms",method = RequestMethod.GET)
    public ResponseVO getFilms(FilmRequestVO filmRequestVO) {
//        FilmConditionVO filmConditionVO = new FilmConditionVO();
        FilmVO filmVO = null;
        //根据showType判断影片查询类型
        //根据sortId排序
        //根据各种条件查询
        //判断是第几页
        switch (filmRequestVO.getShowType()){
            case 1:
                filmVO = filmApi.getHotFilms(false,
                        filmRequestVO.getPageSize(),filmRequestVO.getNowPage(),filmRequestVO.getSortId(),
                        filmRequestVO.getSourceId(),filmRequestVO.getYearId(),filmRequestVO.getCatId());
            case 2:
                filmVO = filmApi.getSoonFilms(false,
                    filmRequestVO.getPageSize(),filmRequestVO.getNowPage(),filmRequestVO.getSortId(),
                    filmRequestVO.getSourceId(),filmRequestVO.getYearId(),filmRequestVO.getCatId());
            case 3:
                filmVO = filmApi.getClassicFilms(
                    filmRequestVO.getPageSize(),filmRequestVO.getNowPage(),filmRequestVO.getSortId(),
                    filmRequestVO.getSourceId(),filmRequestVO.getYearId(),filmRequestVO.getCatId());
            default:
                filmVO = filmApi.getHotFilms(false,
                        filmRequestVO.getPageSize(),filmRequestVO.getNowPage(),filmRequestVO.getSortId(),
                        filmRequestVO.getSourceId(),filmRequestVO.getYearId(),filmRequestVO.getCatId());
        }

        return ResponseVO.success(
                            filmVO.getFilmInfo(),IMG_PRE,
                            filmVO.getTotalNowPge(),filmVO.getTotalPage());
    }

    /**
     * 影片详情信息查询接口
     * @return
     */
    @RequestMapping(value="films/{searchParam}",method = RequestMethod.GET)
    public ResponseVO films(@PathVariable("searchParam")String searchParam,
                            int searchType) throws ExecutionException, InterruptedException {

        //根据searchType判断查询类型
        FilmDetailVO filmDetail = filmApi.getFilmDetail(searchType, searchParam);

        if(filmDetail == null){
            return ResponseVO.serviceFail("没有可查询的影片信息");
        }else if(filmDetail.getFilmId() == null ||  filmDetail.getFilmId().trim().length() == 0){
            return ResponseVO.serviceFail("没有可查询的影片信息");
        }

        String filmId = filmDetail.getFilmId();
        //不同的查询类型传入的条件会略有不同【】
        
        //查询影片的详细信息 -> dubbo 异步获取
        //获取影片描述信息
//        FilmDescVO filmDesc = filmAsyncApi.getFilmDesc(filmDetail.getFilmId());
        filmAsyncApi.getFilmDesc(filmId);
        Future<FilmDescVO> filmDescVOFuture = RpcContext.getContext().getFuture();
        //获取图片信息
        filmAsyncApi.getImgs(filmId);
        Future<ImgVO> imgVOFuture = RpcContext.getContext().getFuture();
        //获取导演信息
        filmAsyncApi.getDescInfo(filmId);
        Future<ActorVO> descFuture = RpcContext.getContext().getFuture();
        //获取演员信息
        filmAsyncApi.getActors(filmId);
        Future<List<ActorVO>> actorVOFuture = RpcContext.getContext().getFuture();
        //将返回数据组装至 FilmDetailVO 对象中
        InfoRequestVO infoRequestVO = new InfoRequestVO();
        ActorRequestVO actorRequestVO = new ActorRequestVO();
        actorRequestVO.setActors(actorVOFuture.get());
        actorRequestVO.setDirector(descFuture.get());
        infoRequestVO.setActors(actorRequestVO);
        infoRequestVO.setImgs(imgVOFuture.get());
        infoRequestVO.setBiography(filmDescVOFuture.get().getBiography());
        infoRequestVO.setFilmId(filmDetail.getFilmId());

        filmDetail.setInfo04(infoRequestVO);
        return ResponseVO.success(filmDetail,IMG_PRE);
    }

}
