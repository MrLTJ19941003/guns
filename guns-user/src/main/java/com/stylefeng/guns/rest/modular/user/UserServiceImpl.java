package com.stylefeng.guns.rest.modular.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.api.user.UserApi;
import com.stylefeng.guns.api.user.vo.UserInfoModel;
import com.stylefeng.guns.api.user.vo.UserModel;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.rest.common.persistence.dao.MoocUserTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MoocUserT;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by liutj on 2019/2/28.
 */
@Component
@Service(interfaceClass = UserApi.class)
public class UserServiceImpl implements UserApi{

    private final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private MoocUserTMapper moocUserTMapper;

    @Override
    public int login(String username, String password) {
        MoocUserT moocUserT = new MoocUserT();
        moocUserT.setUserName(username);
        moocUserT.setUserPwd(MD5Util.encrypt(password));//密码需要用到MD5加密
        MoocUserT moocUserTOne = moocUserTMapper.selectOne(moocUserT);
        logger.info("login select successed,result : [" + moocUserT + "]");
        if(moocUserTOne != null && moocUserTOne.getUuid() > 0){
            return moocUserTOne.getUuid();
        }
        return 0;
    }

    @Override
    public boolean register(UserModel userModel) {
        //将注册信息实体转换为数据实体[mooc_user_t]
        MoocUserT moocUserT = new MoocUserT();
        moocUserT.setUserName(userModel.getUsername());
        moocUserT.setEmail(userModel.getEmail());
        moocUserT.setUserPhone(userModel.getPhone());
        moocUserT.setUserPwd(MD5Util.encrypt(userModel.getPassword()));//密码需要用到MD5加密
        //插入
        Integer resultCount = moocUserTMapper.insert(moocUserT);
        logger.info("register insert successed,result : [" + resultCount + "]");
        if(resultCount > 0)
            return true;
        return false;
    }

    @Override
    public boolean checkUserName(String username) {
        EntityWrapper<MoocUserT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("user_name",username);
        Integer result = moocUserTMapper.selectCount(entityWrapper);
        if(result != null && result > 0)
            return false;
        return true;
    }

    private UserInfoModel do2UserInfo(MoocUserT moocUserT,boolean isHavingUUID){
        UserInfoModel userInfoModel = new UserInfoModel();
        if(!isHavingUUID)
            userInfoModel.setUuid(moocUserT.getUuid());
        userInfoModel.setUsername(moocUserT.getUserName());
        userInfoModel.setNickname(moocUserT.getNickName());
        userInfoModel.setEmail(moocUserT.getEmail());
        userInfoModel.setPhone(moocUserT.getUserPhone());
        userInfoModel.setAddress(moocUserT.getAddress());
        userInfoModel.setHeadAddress(moocUserT.getHeadUrl());
        userInfoModel.setSex(moocUserT.getUserSex());
        userInfoModel.setLifeState(""+moocUserT.getLifeState());
        userInfoModel.setBiography(moocUserT.getBiography());
        userInfoModel.setBirthday(moocUserT.getBirthday());
        userInfoModel.setBeginTime(moocUserT.getBeginTime().getTime());
        userInfoModel.setUpdateTime(moocUserT.getUpdateTime().getTime());

        return userInfoModel;
    }

    @Override
    public UserInfoModel getUserInfo(int uuid) {
        //根据主键查询用户信息[MoocUserT]
        MoocUserT moocUserT = moocUserTMapper.selectById(uuid);
        //将MoocUserT转换成UserInfoModel
        UserInfoModel userInfoModel = do2UserInfo(moocUserT,false);
        //返回UserInfoModel
        return userInfoModel;
    }

    private Date time2Date(long time){
        Date date = new Date(time);
        return date;
    }

    private MoocUserT do2MoocUserT(UserInfoModel userInfoModel){
        MoocUserT moocUserT = new MoocUserT();
        moocUserT.setUuid(userInfoModel.getUuid());
        if(userInfoModel.getUsername() != null && userInfoModel.getUsername().length() > 0)
            moocUserT.setUserName(userInfoModel.getUsername());
        if(userInfoModel.getEmail() != null && userInfoModel.getEmail().length() > 0)
            moocUserT.setEmail(userInfoModel.getEmail());
        if(userInfoModel.getPhone() != null && userInfoModel.getPhone().length() > 0)
            moocUserT.setUserPhone(userInfoModel.getPhone());
        if(userInfoModel.getAddress() != null && userInfoModel.getAddress().length() > 0)
            moocUserT.setAddress(userInfoModel.getAddress());
        if(userInfoModel.getHeadAddress() != null && userInfoModel.getHeadAddress().length() > 0)
            moocUserT.setHeadUrl(userInfoModel.getHeadAddress());
        if(userInfoModel.getBiography() != null && userInfoModel.getBiography().length() > 0)
            moocUserT.setBiography(userInfoModel.getBiography());
        if(userInfoModel.getBirthday() != null && userInfoModel.getBirthday().length() > 0)
            moocUserT.setBirthday(userInfoModel.getBirthday());
        if(userInfoModel.getLifeState() != null && userInfoModel.getLifeState().length() > 0)
            moocUserT.setLifeState(Integer.parseInt(userInfoModel.getLifeState()));
        moocUserT.setUserSex(userInfoModel.getSex());
        if(userInfoModel.getNickname() != null && userInfoModel.getNickname().length() > 0)
            moocUserT.setNickName(userInfoModel.getNickname());
        if(userInfoModel.getBeginTime() != 0)
            moocUserT.setBeginTime(time2Date(userInfoModel.getBeginTime()));
        if(userInfoModel.getUpdateTime() != 0)
            moocUserT.setUpdateTime(time2Date(userInfoModel.getUpdateTime()));

        return moocUserT;
    }

    @Override
    public UserInfoModel updateUserInfo(UserInfoModel userInfoModel) {
        //将注册信息实体转换为数据实体[mooc_user_t]
        MoocUserT moocUserT = do2MoocUserT(userInfoModel);
        //将数据库存入数据库
        Integer result = moocUserTMapper.updateById(moocUserT);
        //判断是否插入成功
        if(result > 0){
            //按照ID将用户信息查询出来
            UserInfoModel userInfo = getUserInfo(moocUserT.getUuid());
            //返回
            return userInfo;
        }
        //返回
        return userInfoModel;
    }
}
