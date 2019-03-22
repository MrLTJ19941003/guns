package com.stylefeng.guns.api.user;

import com.stylefeng.guns.api.user.vo.UserInfoModel;
import com.stylefeng.guns.api.user.vo.UserModel;

/**
 * Created by liutj on 2019/2/28.
 */
public interface UserApi {

    int login(String username, String password);

    boolean register(UserModel userModel);

    boolean checkUserName(String username);

    UserInfoModel getUserInfo(int uuid);

    UserInfoModel updateUserInfo(UserInfoModel userInfoModel);
}
