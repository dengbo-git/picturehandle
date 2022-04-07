package fun.china1.picturehandle.controller.base;

import com.alibaba.fastjson.JSONObject;
import fun.china1.picturehandle.utils.QiNiuUtil;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * @author ：波波
 * @date ：Created in 2022/3/18 19:31
 * @description：基础server类
 */
public class BaseController {


    @Resource
    RedisTemplate redisTemplate;


    /**
     * 获取用户的openId。因为添加了过滤器，所以token不可能null
     * @param token
     * @return
     */
    public String getOpenid(String token) {

        return (String) redisTemplate.opsForValue().get(token);

    }

}
