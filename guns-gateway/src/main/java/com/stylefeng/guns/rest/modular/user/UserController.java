package com.stylefeng.guns.rest.modular.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.user.UserApi;
import com.stylefeng.guns.api.user.vo.UserInfoModel;
import com.stylefeng.guns.api.user.vo.UserModel;
import com.stylefeng.guns.rest.common.CurrentUser;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by liutj on 2019/3/5.
 */
@RestController
@RequestMapping("/user/")
public class UserController {

    @Reference(interfaceClass = UserApi.class,check = false)
    private UserApi userApi;

    @RequestMapping(value="register",method = RequestMethod.POST)
    public ResponseVO register(UserModel userModel){

        if(userModel.getUsername() == null || userModel.getUsername().trim().length() == 0){
            return ResponseVO.serviceFail("用户名不能为空");
        }

        if(userModel.getPassword() == null || userModel.getPassword().trim().length() == 0){
            return ResponseVO.serviceFail("密码不能为空");
        }

//        if(!userApi.checkUserName(userModel.getUsername())){
//            return ResponseVO.serviceFail("用户名已存在");
//        }
        boolean isSuccess = userApi.register(userModel);
        if(isSuccess){
            return ResponseVO.success("注册成功");
        }
        return ResponseVO.serviceFail("注册失败");

    }

    @RequestMapping(value="check",method = RequestMethod.POST)
    public ResponseVO check(String username){

        if(username == null || username.trim().length() == 0){
            return ResponseVO.serviceFail("用户名必须存在");
        }

        if(!userApi.checkUserName(username)){
            return ResponseVO.serviceFail("用户名已存在");
        }
        return ResponseVO.success("用户名不存在");

    }

    @RequestMapping(value="logout",method = RequestMethod.GET)
    public ResponseVO logout(){



        return ResponseVO.success("用户退出成功");

    }

    @RequestMapping(value="getUserInfo",method = RequestMethod.GET)
    public ResponseVO getUserInfo(){
        String userID = CurrentUser.getUserID();
        if(userID != null || userID.trim().length() > 0){
            int userid = Integer.parseInt(userID);
            UserInfoModel userInfo = userApi.getUserInfo(userid);
            if(userInfo != null)
                return ResponseVO.success(userInfo);
            return ResponseVO.serviceFail("用户信息查询失败");
        }
        return ResponseVO.success("用户未登录");

    }

    @RequestMapping(value="updateUserInfo",method = RequestMethod.POST)
    public ResponseVO updateUserInfo(UserInfoModel userInfoModel){

        String userID = CurrentUser.getUserID();
        if(userID != null || userID.trim().length() > 0){
            int userid = Integer.parseInt(userID);
            if(userid == userInfoModel.getUuid()){
                UserInfoModel userInfo = userApi.updateUserInfo(userInfoModel);
                if(userInfo != null)
                    return ResponseVO.success(userInfo);
                return ResponseVO.serviceFail("用户信息修改失败");
            }else{
                return ResponseVO.success("请修改您个人信息");
            }

        }
        return ResponseVO.success("用户未登录");

    }
}
