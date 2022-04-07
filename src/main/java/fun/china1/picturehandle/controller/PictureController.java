package fun.china1.picturehandle.controller;

import com.alibaba.fastjson.JSONObject;
import com.sun.javafx.scene.control.behavior.SpinnerBehavior;
import fun.china1.picturehandle.controller.base.BaseController;
import fun.china1.picturehandle.domain.WxUserPicture;
import fun.china1.picturehandle.service.PictureHandle;
import fun.china1.picturehandle.utils.QiNiuUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ：波波
 * @date ：Created in 2022/3/15 17:14
 * @description：处理老照片修复的控制器
 * @modified By：
 * @version: 1.0$
 */
@RestController()
@RequestMapping("/handle")
public class PictureController extends BaseController {
    @Resource
    PictureHandle pictureHandle;

    /**
     * 请求上传照片
     * @return 返回上传照片的token
     */
    @RequestMapping(value = "/upLoadImg", method = RequestMethod.GET)
    public JSONObject upLoadImg() {
        JSONObject upLoadToken;
        upLoadToken = pictureHandle.getUpLoadToken();
        return upLoadToken;
    }

    /**
     * 请求处理照片
     * @param token 用户标识的token
     * @param imgUrl 要被处理照片的url
     * @param type 要被处理的类型
     * @return
     */
    @RequestMapping(value = "/handleImg", method = RequestMethod.POST)
    public JSONObject handleImg(String token, String imgUrl, Integer type ,@RequestParam(value = "colorType",required = false) Integer colorType) {
        JSONObject handleResult = null;
        handleResult = pictureHandle.handlePicture(getOpenid(token),imgUrl, type,colorType);
        return handleResult;
    }
}
