package fun.china1.picturehandle.domain;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Map;

/**
 * @author ：波波
 * @date ：Created in 2022/3/18 16:16
 * @description：微信用户
 * @modified By：
 * @version: $1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("wx_user")
@ToString
public class WxUser {

    /**
     * 自然主键
     */
    @TableField("id")
    private String id;

    /**
     * 微信用户的唯一标识
     */
    @TableField("wx_openid")
    private String wxOpenid;

    /**
     * 该用户的微信名称
     */
    @TableField("wx_nickname")
    private String wxNickname;

    /**
     * 该用户的性别
     */
    @TableField("wx_sex")
    private Integer wxSex;

    /**
     * 该用户头像的地址
     */
    @TableField("wx_avatar_url")
    private String wxAvatarUrl;

    /**
     * 用户所在的城市
     */
    @TableField("wx_city")
    private String wxCity;

    /**
     * 用户的国家
     */
    @TableField("wx_country")
    private String wxCountry;

    /**
     * 用户使用的语言
     */
    @TableField("wx_language")
    private String wxLanguage;
    /**
     * 登录状态
     */
    @TableField("wx_login_state")
    private Integer wxLoginState;

    /**
     * 是否被平台封禁
     */
    @TableField("wx_ban")
    private Integer wxBan;

    /**
     * 用户被封禁的原因
     */
    @TableField("wx_ban_reason")
    private String wxBanReason;


    public WxUser(String wxOpenid, Map<String,String> userInfo,Integer wxLoginState) {
        this.wxOpenid = wxOpenid;
        this.wxLoginState = wxLoginState;
        this.wxNickname = userInfo.get("nickName");
        this.wxSex = Integer.valueOf(userInfo.get("gender"));
        this.wxAvatarUrl = userInfo.get("avatarUrl");
        this.wxCity = userInfo.get("city");
        this.wxCountry = userInfo.get("country");
        this.wxLanguage = userInfo.get("language");
    }


    public JSONObject toJSON() {
        JSONObject resultJson = new JSONObject();
        resultJson.put("wxNickname",this.wxNickname);
        resultJson.put("wxSex",this.wxSex);
        resultJson.put("wxAvatarUrl",this.wxAvatarUrl);
        resultJson.put("wxCity",this.wxCity);
        resultJson.put("wxCountry",this.wxCountry);
        resultJson.put("wxLanguage",this.wxLanguage);

        return resultJson;
    }
}
