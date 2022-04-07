package fun.china1.picturehandle.controller.person;

import com.alibaba.fastjson.JSONObject;
import fun.china1.picturehandle.controller.base.BaseController;
import fun.china1.picturehandle.service.person.Suggest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author ：波波
 * @date ：Created in 2022/3/23 12:38
 * @description：发送建议的控制器
 */
@RestController()
@RequestMapping("/suggest")
public class SuggestController extends BaseController {

    @Resource
    private Suggest suggest;

    @PostMapping(value = "/suggest")
    public JSONObject suggest(@RequestParam("token") String token,@RequestParam("title") String title,@RequestParam("content") String content,@RequestParam("contact") String contact){
        String openid = getOpenid(token);
        JSONObject returnValue = suggest.handleSuggest(openid, title, content, contact);
        return returnValue;
    }
}
