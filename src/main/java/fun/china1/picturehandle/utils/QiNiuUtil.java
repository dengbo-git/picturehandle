package fun.china1.picturehandle.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.UnsupportedEncodingException;


/**
 * @author ：波波
 * @date ：Created in 2022/3/15 20:49
 * @description：七牛云的有关操作
 * @modified By：
 * @version: $
 */
@Data
@ConfigurationProperties("qiniu")
public class QiNiuUtil {
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String domainOfBucket;

    /**
     * 获取上传的token
     */
    public JSONObject getUpLoadToken() {
        Auth auth = Auth.create(accessKey, secretKey);
        StringMap putPolicy = new StringMap();

        long expireSeconds = 60;
        String Token = auth.uploadToken(bucket, null, expireSeconds, putPolicy);
        JSONObject upToken = new JSONObject();
        try {
            upToken.put("uptoken", Token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        upToken.put("state", "1");
        return upToken;
    }

    /**
     * 得到要被处理的图片可访问的url
     */
    public String downloadRawImg(String rawImgUrl) {
        Auth auth = Auth.create(accessKey, secretKey);
        String privateUrl = domainOfBucket + rawImgUrl;
        String downloadRUL = auth.privateDownloadUrl(privateUrl, 600);
        return downloadRUL;
    }

    /**
     * 上传图片
     */
    public JSONObject uploadFile(byte[] img) throws QiniuException {
        Configuration cfg = new Configuration(Region.huabei());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        JSONObject returnJson = new JSONObject();

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        Response response = uploadManager.put(img, key, upToken);
        //解析上传成功的结果
        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        returnJson.put("key", putRet.key);
        returnJson.put("hash", putRet.hash);
        System.out.println(putRet.key);
        System.out.println(putRet.hash);
        return returnJson;
    }
}
