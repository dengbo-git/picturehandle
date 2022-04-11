package fun.china1.picturehandle.service.impl.person;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.china1.picturehandle.dao.WxUserPictureMapper;
import fun.china1.picturehandle.domain.WxUserPicture;
import fun.china1.picturehandle.service.person.HistoryShow;
import fun.china1.picturehandle.service.base.WxServiceImpl;
import fun.china1.picturehandle.utils.QiNiuUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * @author ：波波
 * @date ：Created in 2022/3/22 18:28
 * @description：
 * @modified By：
 * @version: $
 */
@Service
public class HistoryShowImpl extends WxServiceImpl<WxUserPictureMapper, WxUserPicture> implements HistoryShow {

    @Resource
    private QiNiuUtil qiNiuUtil;

    @Override
    public JSONArray getData(Integer lastId,Integer needSize,String openId) {
        JSONArray returnValue = new JSONArray(new LinkedList<>());
        QueryWrapper<WxUserPicture> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(WxUserPicture::getWxOpenid,openId);
        queryWrapper.lambda().eq(WxUserPicture::getWxImgDelete,0);
        queryWrapper.lambda().orderBy(openId!=null,false,WxUserPicture::getWxOperateTime);
        if (lastId != -1) {
            queryWrapper.lambda().lt(WxUserPicture::getId,lastId);
        }
        queryWrapper.last("limit "+needSize);
        List<WxUserPicture> wxUserPictures = baseMapper.selectList(queryWrapper);
        wxUserPictures.forEach(picture ->{
            try{
                String publicRawImgUrl = qiNiuUtil.downloadRawImg(picture.getWxImgRawUrl());
                String publicHandleImgUrl = qiNiuUtil.downloadRawImg(picture.getWxImgHandleUrl());
                picture.setWxImgRawUrl(publicRawImgUrl);
                picture.setWxImgHandleUrl(publicHandleImgUrl);
            }catch (Exception e){
                sendErrorEmail("用户拉取历史记录失败",e,this.getClass());
            }
            returnValue.add(picture.toJSON());
        } );

        return returnValue;
    }

    @Override
    public JSONObject deleteImg(String openId, String ImgId) {
        JSONObject deleteResult = new JSONObject();
        try {
            UpdateWrapper<WxUserPicture> updateWrapper = new UpdateWrapper<>();
            updateWrapper.lambda().eq(WxUserPicture::getWxOpenid, openId);
            updateWrapper.lambda().eq(WxUserPicture::getId, ImgId);
            updateWrapper.lambda().set(WxUserPicture::getWxImgDelete, 1);
            baseMapper.update(new WxUserPicture(), updateWrapper);
            deleteResult.put("msg","删除成功");
            deleteResult.put("state","1");
        }catch (Exception e){
            sendErrorEmail("用户照片删除失败",e,this.getClass());
            deleteResult.put("msg","删除失败");
            deleteResult.put("state","3");
        }
        return deleteResult;
    }
}
