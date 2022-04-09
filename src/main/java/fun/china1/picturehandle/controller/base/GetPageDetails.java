package fun.china1.picturehandle.controller.base;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.china1.picturehandle.dao.WxSettingMapper;
import fun.china1.picturehandle.domain.WxSetting;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ：波波
 * @date ：Created in 2022/4/10 2:50
 */

@RestController
@RequestMapping("/pageDetail")
public class GetPageDetails extends ServiceImpl<WxSettingMapper, WxSetting> {
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public JSONObject getPageDetailDate() {
        JSONObject returnValue = new JSONObject();
        QueryWrapper<WxSetting> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(WxSetting::getWxIsAble,1);
        queryWrapper.lambda().eq(WxSetting::getWxIsFunction,1);
        queryWrapper.lambda().orderBy(true,true,WxSetting::getWxHandleType);
        //查到所有功能模块
        List<WxSetting> functionList = baseMapper.selectList(queryWrapper);
        //查到所有的素材
        queryWrapper.lambda().clear();
        queryWrapper.lambda().eq(WxSetting::getWxIsAble,1);
        queryWrapper.lambda().eq(WxSetting::getWxIsFunction,0);
        queryWrapper.lambda().orderBy(true,true,WxSetting::getWxHandleType);
        List<WxSetting> imgList = baseMapper.selectList(queryWrapper);
        JSONArray functionsItems = new JSONArray();
        JSONArray imgItems = new JSONArray();
        //过滤出来
        functionList.forEach(function->{
            JSONObject jsonObject = function.toFunctionJSON();
            functionsItems.add(jsonObject);
        });
        imgList.forEach(function->{
            JSONObject jsonObject = function.toImgJSON();
            imgItems.add(jsonObject);
        });
        returnValue.put("imgs",imgItems);
        returnValue.put("functionItems",functionsItems);
        return returnValue;
    }
}
