package fun.china1.picturehandle.service.person;

import com.alibaba.fastjson.JSONObject;

import javax.mail.MessagingException;

/**
 * @author dengbo
 * 处理建议的service
 */
public interface Suggest {

    /**
     * 处理'建议'
     * @param openId
     * @param title
     * @param content
     * @param contact
     * @return
     */
    JSONObject handleSuggest(String openId,String title,String content,String contact);

}
