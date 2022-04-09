package fun.china1.picturehandle.domain;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 
 * @author dengbo
 * @TableName wx_setting
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("wx_setting")
@ToString
public class WxSetting implements Serializable {
    /**
     * 自然主键
     */
    @TableField("id")
    private Integer id;

    /**
     * 名字
     */
    @TableField("wx_name")
    private String wxName;

    /**
     * 背景图片
     */
    @TableField("wx_img_url")
    private String wxImgUrl;

    /**
     * 路由
     */
    @TableField("wx_route")
    private String wxRoute;

    /**
     * 被处理的类型
     */
    @TableField("wx_handle_type")
    private Integer wxHandleType;

    /**
     * 该模块的属性，0是素材，1是模块
     */

    @TableField("wx_is_function")
    private Integer wxIsFunction;

    /**
     * 是否有效
     */
    @TableField("wx_is_able")
    private Integer wxIsAble;


    public JSONObject toImgJSON() {
        JSONObject resultJson = new JSONObject();
        resultJson.put("imgUrl",this.wxImgUrl);
        return resultJson;
    }
    public JSONObject toFunctionJSON() {
        JSONObject resultJson = new JSONObject();
        resultJson.put("functionName",this.wxName);
        resultJson.put("functionImgUrl",this.wxImgUrl);
        resultJson.put("functionRoute",this.wxRoute);
        resultJson.put("functionHandleType",this.wxHandleType);

        return resultJson;
    }


}