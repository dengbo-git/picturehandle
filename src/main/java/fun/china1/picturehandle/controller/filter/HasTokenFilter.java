package fun.china1.picturehandle.controller.filter;

import com.alibaba.fastjson.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author ：波波
 * @date ：Created in 2022/3/21 7:56
 * @description：检查客户端传入的token是否合法
 */
@Component
public class HasTokenFilter implements HandlerInterceptor {
    @Resource
    RedisTemplate redisTemplate;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getParameter("token");
        JSONObject returnError = new JSONObject();
        if (StringUtils.hasLength(token)) {
            ValueOperations ops = redisTemplate.opsForValue();
            String openid = (String) ops.get(token);
            if (StringUtils.hasLength(openid)) {
                return true;
            } else {
                returnError.put("msg", "token outOf date！");
                returnError.put("state", "2");
            }
        } else {
            returnError.put("msg", "not LogIn！");
            returnError.put("state", "0");
        }
        PrintWriter writer = response.getWriter();
        writer.print(returnError);
        writer.flush();
        return false;
    }
}
