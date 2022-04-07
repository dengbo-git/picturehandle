package fun.china1.picturehandle.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import fun.china1.picturehandle.domain.WxUser;

import java.util.Map;

/**
 * @author ：波波
 * @date ：Created in 2022/3/17 21:47
 * @description：登录进来的方法
 */
public interface LoginSystem extends IService<WxUser> {
    /**
     * 登录进入的方法
     * @param userInfo 用户的信息
     * @return
     */
    JSONObject loginInto(Map<String,String> userInfo);

    /**
     * 退出登录的方法
     * @param token
     * @return
     */
    JSONObject loginOut(String token);


}
