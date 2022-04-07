package fun.china1.picturehandle.controller.person;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import fun.china1.picturehandle.controller.base.BaseController;
import fun.china1.picturehandle.service.person.HistoryShow;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ：波波
 * @date ：Created in 2022/3/22 18:23
 * @description：查询历史记录的控制器
 */
@RestController()
@RequestMapping("/history")
public class HistoryShowController extends BaseController {

    @Resource
    HistoryShow historyShow;

    @RequestMapping("/getData")
    public JSONObject getData(@RequestParam("pageNumber") Integer pageNumber, @RequestParam("pageSize") Integer pageSize, @RequestParam("token") String token){
        JSONObject returnValue = new JSONObject();
        JSONArray data = historyShow.getData(pageNumber, pageSize, getOpenid(token));
        returnValue.put("data",data);
        returnValue.put("state","1");
        return returnValue;
    }
    /**
     * 删除照片
     * @param token
     * @param imgId
     * @return
     */
    @RequestMapping(value = "/deleteImg",method = RequestMethod.DELETE)
    public JSONObject deleteImg(String token,String imgId){
        JSONObject deleteResult = null;
        deleteResult = historyShow.deleteImg(getOpenid(token),imgId);
        return deleteResult;
    }
}
