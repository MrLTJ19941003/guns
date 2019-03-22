package com.stylefeng.guns.api.film;

import com.stylefeng.guns.api.film.vo.*;

import java.util.List;

/**
 * Created by liutj on 2019/3/14.
 */
public interface FilmAsyncApi {

    //获取影片描述信息
    FilmDescVO getFilmDesc(String uuid);
    //获取图片信息
    ImgVO getImgs(String filmId);
    //获取导演信息
    ActorVO getDescInfo(String filmId);
    //获取演员信息
    List<ActorVO> getActors(String filmId);

}
