package com.stylefeng.guns.api.film.vo;

import com.stylefeng.guns.api.film.vo.ActorVO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liutj on 2019/3/21.
 */
@Data
public class ActorRequestVO  implements Serializable {

    private ActorVO director;
    private List<ActorVO> actors;

}
