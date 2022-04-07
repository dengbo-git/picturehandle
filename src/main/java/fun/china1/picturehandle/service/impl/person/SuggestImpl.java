package fun.china1.picturehandle.service.impl.person;

import com.alibaba.fastjson.JSONObject;
import fun.china1.picturehandle.dao.WxUserSuggestMapper;
import fun.china1.picturehandle.domain.WxUserSuggest;
import fun.china1.picturehandle.service.base.WxServiceImpl;
import fun.china1.picturehandle.service.person.Suggest;
import fun.china1.picturehandle.utils.EmailUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author ：波波
 * @date ：Created in 2022/3/23 12:50
 * @description：
 * @modified By：
 * @version: $
 */
@Service
public class SuggestImpl extends WxServiceImpl<WxUserSuggestMapper, WxUserSuggest> implements Suggest {


    @Override
    public JSONObject handleSuggest(String openId, String title, String content, String contact) {
        int sendState = 0;
        String notSendReason = "";
        JSONObject returnValue = new JSONObject();
        try {
            getEmailUtils().sendEmail(title, content, contact);
            sendState = 1;
            returnValue.put("state", 1);
            returnValue.put("msg", "已发送至管理员邮箱！");
        } catch (Exception e) {
            notSendReason = e.getMessage();
            sendState = 0;
            returnValue.put("state", "3");
            returnValue.put("msg", "发送失败");
            sendErrorEmail( "您有一条建议发送失败，请前往后台查看",e,this.getClass());
        } finally {
            save(new WxUserSuggest(null,openId, title, content, contact, getEmailUtils().getAdminReceiveEmail(), sendState, notSendReason));
        }
        return returnValue;
    }


}
