package fun.china1.picturehandle.utils;

import com.baidu.aip.bodyanalysis.AipBodyAnalysis;
import com.baidu.aip.imageprocess.AipImageProcess;
import lombok.Data;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author ：波波
 * @date ：Created in 2022/3/28 20:11
 */

@ConfigurationProperties("baidu")
@Data
public class BaiDuImgHandle {

    private String appId;
    private String apiKey;
    private String secretKey;


    @Resource
    private RestTemplate restTemplate;


    /**
     * 图片动漫化
     *
     * @param handleImgUrl
     */
    public byte[] toComic(String handleImgUrl) {
        AipImageProcess client = new AipImageProcess(appId, apiKey, secretKey);
        byte[] rawImgToByte = getRawImgToByte(handleImgUrl);
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("type", "anime");
        JSONObject res = client.selfieAnime(rawImgToByte, options);
        byte[] img = null;
        img = Base64.decodeBase64(res.getString("image"));
        return img;
    }

    /**
     * 图片更换底色
     *
     * @param handleImgUrl
     */
    public byte[] toBgColorChange(String handleImgUrl,Integer colorType) throws IOException {
        AipBodyAnalysis client = new AipBodyAnalysis(appId, apiKey, secretKey);
        Color color = null;
        switch (colorType){
            case 1:color = Color.BLUE;break;
            case 2:color = Color.RED;break;
            case 3:color = Color.WHITE;break;
            case 4:color = Color.BLACK;break;
        }
        byte[] rawImgToByte = getRawImgToByte(handleImgUrl);
        HashMap<String, String> options = new HashMap<String, String>(0);
        JSONObject res = client.bodySeg(rawImgToByte, options);
        byte[] img = null;

        img = Base64.decodeBase64(res.getString("foreground"));

        if (color == null) {
            return img;
        }
        ByteArrayInputStream in = new ByteArrayInputStream(img);
        BufferedImage image = ImageIO.read(in);
        BufferedImage result = new BufferedImage(
                image.getWidth(),
                image.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        Graphics2D graphic = result.createGraphics();
        graphic.drawImage(image, 0, 0, color, null);
        graphic.dispose();
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        ImageIO.write(result, "jpg", bao);
        byte[] handleResult = bao.toByteArray();
        bao.flush();
        bao.close();
        return handleResult;
    }


    /**
     * 图片清晰度增强
     *
     * @param handleImgUrl
     * @return 被处理好的照片
     * @throws IOException
     */
    public byte[] toClear(String handleImgUrl) {
        AipImageProcess client = new AipImageProcess(appId, apiKey, secretKey);
        byte[] rawImgToByte = getRawImgToByte(handleImgUrl);
        HashMap<String, String> options = new HashMap<String, String>();
        JSONObject res = client.imageDefinitionEnhance(rawImgToByte, options);
        byte[] img = null;
        img = Base64.decodeBase64(res.getString("image"));
        return img;
    }

    /**
     * 黑白照片上色
     *
     * @param handleImgUrl
     * @return 被处理好的照片
     * @throws IOException
     */
    public byte[] toColorInOldPhotos(String handleImgUrl) {
        AipImageProcess client = new AipImageProcess(appId, apiKey, secretKey);
        byte[] rawImgToByte = getRawImgToByte(handleImgUrl);
        HashMap<String, String> options = new HashMap<String, String>();
        JSONObject res = client.colourize(rawImgToByte, options);
        byte[] img = null;
        img = Base64.decodeBase64(res.getString("image"));
        return img;
    }

    /**
     * 图片无损放大
     *
     * @param handleImgUrl
     * @return
     * @throws IOException
     */
    public byte[] toPhotoZoomPro(String handleImgUrl) {
        AipImageProcess client = new AipImageProcess(appId, apiKey, secretKey);
        byte[] rawImgToByte = getRawImgToByte(handleImgUrl);
        HashMap<String, String> options = new HashMap<String, String>();
        JSONObject res = client.imageQualityEnhance(rawImgToByte, options);
        byte[] img = null;
        img = Base64.decodeBase64(res.getString("image"));
        return img;
    }

    /**
     * 图像对比度增强
     *
     * @param handleImgUrl
     * @return
     * @throws IOException
     */
    public byte[] toContrastEnhancement(String handleImgUrl) {
        AipImageProcess client = new AipImageProcess(appId, apiKey, secretKey);
        byte[] rawImgToByte = getRawImgToByte(handleImgUrl);
        HashMap<String, String> options = new HashMap<String, String>();
        JSONObject res = client.contrastEnhance(rawImgToByte, options);
        byte[] img = null;
        img = Base64.decodeBase64(res.getString("image"));
        return img;
    }

    /**
     * 拉伸图像修复
     *
     * @param handleImgUrl
     * @return
     * @throws IOException
     */
    public byte[] toStretchRepair(String handleImgUrl) {
        AipImageProcess client = new AipImageProcess(appId, apiKey, secretKey);
        byte[] rawImgToByte = getRawImgToByte(handleImgUrl);
        HashMap<String, String> options = new HashMap<String, String>();
        JSONObject res = client.stretchRestore(rawImgToByte, options);
        byte[] img = null;
        img = Base64.decodeBase64(res.getString("image"));
        return img;
    }

    /**
     * 通过url获取要被处理的图像
     */
    public byte[] getRawImgToByte(String publicRawImgUrl) {
        ResponseEntity<byte[]> entity = restTemplate.postForEntity(publicRawImgUrl, null, byte[].class);
        byte[] img = entity.getBody();
        return img;
    }


}
