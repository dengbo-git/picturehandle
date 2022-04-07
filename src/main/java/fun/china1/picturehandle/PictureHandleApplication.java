package fun.china1.picturehandle;

import fun.china1.picturehandle.utils.*;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author dengbo
 */
@SpringBootApplication
@EnableConfigurationProperties({QiNiuUtil.class, WxConfig.class, BaiDuImgHandle.class, EmailUtils.class})

public class PictureHandleApplication {

    public static void main(String[] args) {
        SpringApplication.run(PictureHandleApplication.class, args);
    }



}
