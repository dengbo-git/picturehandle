package fun.china1.picturehandle.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import fun.china1.picturehandle.dao.WxUserMapper;
import fun.china1.picturehandle.domain.WxUser;
import fun.china1.picturehandle.service.LoginSystem;
import fun.china1.picturehandle.service.base.WxServiceImpl;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author ：波波
 * @date ：Created in 2022/3/17 21:50
 */
@Service
public class LoginSystemImpl extends WxServiceImpl<WxUserMapper, WxUser> implements LoginSystem {


    @Override
    public JSONObject loginInto(Map<String, String> userInfo) {
        String jsCode = userInfo.get("jsCode");

        //移除掉map里面的jsCode。后面要做判空来判断是否有其他信息
        userInfo.remove("jsCode");

        //工具方法拿到用户唯一信息
        JSONObject userMsg = getUserMsg(jsCode);
        String openid = userMsg.getString("openid");

        //生成登录token
        String token = UUID.randomUUID().toString().replace("-", "");
        ValueOperations ops = getRedisOps();

        //查询数据库里面是否有这个人
        QueryWrapper<WxUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(WxUser::getWxOpenid, openid);
        WxUser wxUser = getOne(queryWrapper);

        JSONObject returnValue = new JSONObject();
        if (null == wxUser) {
            if (userInfo.isEmpty()) {
                returnValue.put("msg", "please authorize login first !");
                returnValue.put("token", "");
                returnValue.put("state", "0");
            } else {
                WxUser user = new WxUser(openid, userInfo, 1);
                save(user);
                ops.set(token, openid, 30, TimeUnit.MINUTES);
                returnValue.put("token", token);
                returnValue.put("msg", "login Success !");
                returnValue.put("state", "1");
                returnValue.put("user",user.toJSON());
            }

        } else {
            if (wxUser.getWxBan() == 1) {
                returnValue.put("msg", "your account has been banned by the administrator !");
                returnValue.put("token", "");
                returnValue.put("state", "2");
                returnValue.put("reason",wxUser.getWxBanReason());
            } else {
                if (wxUser.getWxLoginState() == 0) {
                    if (userInfo.isEmpty()) {
                        returnValue.put("msg", "please authorize login first !");
                        returnValue.put("token", "");
                        returnValue.put("state", "0");
                    } else {
                        wxUser.setWxLoginState(1);
                        updateById(wxUser);
                        ops.set(token, openid, 30, TimeUnit.MINUTES);
                        returnValue.put("token", token);
                        returnValue.put("msg", "login Success !");
                        returnValue.put("state", "1");
                        returnValue.put("user", wxUser.toJSON());
                    }

                } else {
                    ops.set(token, openid, 30, TimeUnit.MINUTES);
                    returnValue.put("token", token);
                    returnValue.put("msg", "login Success !");
                    returnValue.put("state", "1");
                    returnValue.put("user",wxUser.toJSON());

                }
            }
        }
        return returnValue;
    }

    @Override
    public JSONObject loginOut(String token) {

        JSONObject returnValue = new JSONObject();
        //不能使用
        /**String openId = (String) ops.getAndDelete(token);*/
        String openId = (String) getRedisOps().get(token);
        if (openId != null) {
            UpdateWrapper<WxUser> updateWrapper = new UpdateWrapper();
            updateWrapper.lambda().eq(WxUser::getWxOpenid,openId);
            updateWrapper.lambda().set(WxUser::getWxLoginState,0);
            update(updateWrapper);
            getRedisTemplate().delete(token);
            returnValue.put("msg","exit Success !");
            returnValue.put("state","1");
        }else {
            returnValue.put("msg","Can't get your token,you may need to quit and reenter !");
            returnValue.put("state","0");
        }
        return returnValue;
    }
}
