package fun.china1.picturehandle.domain;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ：波波
 * @date ：Created in 2022/3/15 21:08
 * @description：登录用户的实体
 * @modified By：
 * @version: $
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("wx_user_picture")
public class WxUserPicture {

    /**
     * 自然主键
     */
    @TableField("id")
    private String id;

    /**微信平台的用户的唯一标识
     */
    @TableField("wx_openid")
    String wxOpenid;
    /**
     * 图片源地址
     */
    @TableField("wx_img_raw_url")
    String wxImgRawUrl;
    /**
     * 图片被处理之后的地址
     */
    @TableField("wx_img_handle_url")
    String wxImgHandleUrl;
    /**
     * 被处理的类型
     */
    @TableField("wx_img_type")
    Integer wxImgType;

    /**
     * 是否已被删除
     */
    @TableField("wx_img_delete")
    Integer wxImgDelete;

    /**
     *操作的时间
     */
    @TableField("wx_operate_time")
    Date wxOperateTime;


    public JSONObject toJSON() {
        JSONObject resultJson = new JSONObject();
        resultJson.put("id",this.id);
        resultJson.put("wxImgRawUrl",this.wxImgRawUrl);
        resultJson.put("wxImgHandleUrl",this.wxImgHandleUrl);
        resultJson.put("wxImgType",this.wxImgType);
        resultJson.put("wxOperateTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.wxOperateTime));
        return resultJson;
    }

}
