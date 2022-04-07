package fun.china1.picturehandle.controller;

import com.alibaba.fastjson.JSONObject;
import fun.china1.picturehandle.service.LoginSystem;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author ：波波
 * @date ：Created in 2022/3/17 21:30
 * @description：微信用户登录的方法
 * @modified By：
 * @version: $
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Resource
    LoginSystem loginSystem;

    @PostMapping("/into")
    public JSONObject loginInto(@RequestParam Map<String, String> userInfo) {
        JSONObject loginResult = loginSystem.loginInto(userInfo);
        return loginResult;
    }

    @PostMapping("/out")
    public JSONObject loginOut(@RequestParam("token") String token) {
        JSONObject loginResult = loginSystem.loginOut(token);
        return loginResult;
    }
}
