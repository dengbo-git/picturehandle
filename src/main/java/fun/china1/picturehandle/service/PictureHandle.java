package fun.china1.picturehandle.service;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

/**
 * @author ：波波
 * @date ：Created in 2022/3/18 20:36
 * @description：老照片修复的接口
 */
public interface PictureHandle {


    /**
     * 获取上传的token
     * @return 返回七牛云token
     */
    JSONObject getUpLoadToken();

    /**
     * 处理图片的入口
     * @return 返回修复完成之后的地址以及token
     * @param privateRawImgUrl 需要处理的图片url
     * @param type 处理的类型
     * @param openId 被处理的用户唯一值
     */
    JSONObject handlePicture(String openId,String privateRawImgUrl,Integer ...type);


    /**
     * 人像动漫化,返回imgUrl
     * @return
     * @param rawImgUrl 源照片
     * @throws IOException
     */
    String handleToComic(String rawImgUrl) throws IOException;

    /**
     * 照片更换底色
     * @param rawImgUrl
     * @param colorType
     * @return
     * @throws IOException
     */
    String handleBgColorChange(String rawImgUrl,Integer colorType) throws IOException;

    /**
     * 模糊照片修复
     * @return
     * @param rawImgUrl 源照片
     * @throws IOException
     */
    String handleBlurryRepair(String rawImgUrl) throws IOException;

    /**
     * 老照片上色
     * @param rawImgUrl
     * @return
     * @throws IOException
     */
    String handleColorInOldPhotos(String rawImgUrl) throws IOException;

    /**
     * 图片无损放大
     * @param rawImgUrl
     * @return
     * @throws IOException
     */
    String handlePhotoZoomPro(String rawImgUrl) throws IOException;

    /**
     * 图像对比度增强
     * @param rawImgUrl
     * @return
     * @throws IOException
     */
    String handleContrastEnhancement(String rawImgUrl) throws IOException;

    /**
     * 拉伸图像修复
     * @param rawImgUrl
     * @return
     * @throws IOException
     */
    String handleStretchRepair(String rawImgUrl) throws IOException;

}
