package fun.china1.picturehandle;

import com.baidu.aip.bodyanalysis.AipBodyAnalysis;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

@SpringBootTest
class PictureHandleApplicationTests {


    @Test
    void contextLoads() throws JSONException, IOException {

        AipBodyAnalysis client = new AipBodyAnalysis("25859053", "TKCnuulowh2WkiicAYAAxf3A", "hTjZUoYVjioBQc0fmWerlcWOLrRtoh2x");

        HashMap<String, String> options = new HashMap<String, String>();

        JSONObject res = client.bodySeg("D:\\Desktop\\test\\p0.jpeg", options);
        byte[] img = null;
        img = Base64.decodeBase64(res.getString("foreground"));

        ByteArrayInputStream in = new ByteArrayInputStream(img);
        BufferedImage image = ImageIO.read(in);
        BufferedImage result = new BufferedImage(
                image.getWidth(),
                image.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        Graphics2D graphic = result.createGraphics();
        graphic.drawImage(image, 0, 0, Color.red, null);
        graphic.dispose();
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        ImageIO.write(result, "jpg", bao);
        byte[] handleResult = bao.toByteArray();
        bao.flush();
        bao.close();
    }

}
