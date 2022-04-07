package fun.china1.picturehandle.service.base;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.china1.picturehandle.utils.EmailUtils;
import fun.china1.picturehandle.utils.WxConfig;
import lombok.Data;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：波波
 * @date ：Created in 2022/3/19 21:57
 */
@Data
public class WxServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {
    @Resource
    private RestTemplate restTemplate;

    @Resource
    RedisTemplate redisTemplate;

    @Resource
    WxConfig wxConfig;

    @Resource
    private EmailUtils emailUtils;

    /**
     * redis相关
     *
     * @return
     */
    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public ValueOperations getRedisOps() {
        return redisTemplate.opsForValue();
    }

    /**
     * 获取邮件工具类
     *
     * @return
     */
    public EmailUtils getEmailUtils() {
        return emailUtils;
    }

    /**
     * 获取用户的openid和secret
     *
     * @param jsCode
     * @return
     */
    public JSONObject getUserMsg(String jsCode) {
        String url = wxConfig.getUrl();
        Map<String, String> map = new HashMap<>();
        map.put("js_code", jsCode);
        ResponseEntity<String> responseBody = restTemplate.getForEntity(url, String.class, map);
        String userMsgString = responseBody.getBody();
        JSONObject userMsg = JSONObject.parseObject(userMsgString);
        return userMsg;
    }


    /**
     * 发送错误信息给管理员
     * @param err      报错的异常
     * @param position 报错位置
     */
    public void sendErrorEmail(String msg, Exception err, Class position) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    emailUtils.sendEmail("警告信息！", msg + "\n   " + err.getMessage() + '\n' + "  类" + position.getName() + '\n' + "  行号" + err.getStackTrace()[0].getLineNumber(), null);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
