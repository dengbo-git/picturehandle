package fun.china1.picturehandle.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.QiniuException;
import fun.china1.picturehandle.dao.WxUserMapper;
import fun.china1.picturehandle.dao.WxUserPictureMapper;
import fun.china1.picturehandle.domain.WxUser;
import fun.china1.picturehandle.domain.WxUserPicture;
import fun.china1.picturehandle.service.PictureHandle;
import fun.china1.picturehandle.service.base.WxServiceImpl;
import fun.china1.picturehandle.utils.BaiDuImgHandle;
import fun.china1.picturehandle.utils.EmailUtils;
import fun.china1.picturehandle.utils.QiNiuUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.io.IOException;

/**
 * @author ：波波
 * @date ：Created in 2022/3/18 21:46
 * @description：
 * @modified By：
 * @version: $
 */
@Service
public class PictureHandleImpl extends WxServiceImpl<WxUserPictureMapper, WxUserPicture> implements PictureHandle {

    @Resource
    BaiDuImgHandle baiDuImgHandle;

    @Resource
    private QiNiuUtil qiNiuUtil;


    @Override
    public JSONObject getUpLoadToken() {
        return qiNiuUtil.getUpLoadToken();
    }



    @Override
    public JSONObject handlePicture(String openId, String privateRawImgUrl, Integer ...handleType) {

        //因为储存在私有空间，所以必须先用密钥生成可访问的url
        String publicRawImgUrl = qiNiuUtil.downloadRawImg(privateRawImgUrl);
        //处理好的结果Url(私有)
        String resultUrl = "";
        //本次处理的结果
        JSONObject returnValue = new JSONObject();
        //得到要处理的类型
        Integer type = handleType[0];

        try {
            switch (type) {
                case 1:
                    resultUrl = handleBgColorChange(publicRawImgUrl,handleType[1]!=null?handleType[1]:0);
                    break;
                case 2:
                    resultUrl = handleColorInOldPhotos(publicRawImgUrl);
                    break;
                case 3:
                    resultUrl = handleToComic(publicRawImgUrl);
                    break;
                case 4:
                    resultUrl = handleBlurryRepair(publicRawImgUrl);
                    break;
                case 5:
                    resultUrl = handlePhotoZoomPro(publicRawImgUrl);
                    break;
                case 6:
                    resultUrl = handleContrastEnhancement(publicRawImgUrl);
                    break;
                case 7:
                    resultUrl = handleStretchRepair(publicRawImgUrl);
                    break;
                default: {
                    resultUrl = "unknown Error！";
                }
            }


            //将结果url处理成可以公开的外链
            String publicHandleImgUrl = qiNiuUtil.downloadRawImg(resultUrl);
            returnValue.put("msg", "handle Successful！");
            returnValue.put("image", publicHandleImgUrl);
            returnValue.put("state", "1");
            if(type==1&&null==handleType[1]){
                return returnValue;
            }
            save(new WxUserPicture(null,openId, privateRawImgUrl, resultUrl, type, 0, null));
        } catch (Exception e) {
            returnValue.put("msg", "handle fail！");
            returnValue.put("state", "3");
            sendErrorEmail("照片处理失败", e,this.getClass());
        }


        return returnValue;
    }



    @Override
    public String handleToComic(String rawImgUrl) throws IOException {
        //处理图片
        byte[] img = baiDuImgHandle.toComic(rawImgUrl);

        //将处理好的图片上传，得到图片的hash值
        String hash = qiNiuUtil.uploadFile(img).getString("hash");


        return hash;
    }

    @Override
    public String handleBgColorChange(String rawImgUrl,Integer colorType) throws IOException {
        //处理图片
        byte[] img = baiDuImgHandle.toBgColorChange(rawImgUrl,colorType);

        //将处理好的图片上传，得到图片的hash值
        String hash = qiNiuUtil.uploadFile(img).getString("hash");


        return hash;
    }

    @Override
    public String handleBlurryRepair(String rawImgUrl) throws IOException {
        //处理图片
        byte[] img = baiDuImgHandle.toClear(rawImgUrl);

        //将处理好的图片上传，得到图片的hash值
        String hash = qiNiuUtil.uploadFile(img).getString("hash");

        return hash;
    }
    @Override
    public String handleColorInOldPhotos(String rawImgUrl) throws IOException {
        //处理图片
        byte[] img = baiDuImgHandle.toColorInOldPhotos(rawImgUrl);

        //将处理好的图片上传，得到图片的hash值
        String hash = qiNiuUtil.uploadFile(img).getString("hash");

        return hash;
    }

    @Override
    public String handlePhotoZoomPro(String rawImgUrl) throws IOException {
        //处理图片
        byte[] img = baiDuImgHandle.toPhotoZoomPro(rawImgUrl);

        //将处理好的图片上传，得到图片的hash值
        String hash = qiNiuUtil.uploadFile(img).getString("hash");

        return hash;
    }

    @Override
    public String handleContrastEnhancement(String rawImgUrl) throws IOException {
        //处理图片
        byte[] img = baiDuImgHandle.toContrastEnhancement(rawImgUrl);

        //将处理好的图片上传，得到图片的hash值
        String hash = qiNiuUtil.uploadFile(img).getString("hash");

        return hash;
    }

    @Override
    public String handleStretchRepair(String rawImgUrl) throws IOException {
        //处理图片
        byte[] img = baiDuImgHandle.toStretchRepair(rawImgUrl);

        //将处理好的图片上传，得到图片的hash值
        String hash = qiNiuUtil.uploadFile(img).getString("hash");

        return hash;
    }
}
