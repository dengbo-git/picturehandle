package fun.china1.picturehandle.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author ：波波
 * @date ：Created in 2022/3/17 21:58
 */
@ConfigurationProperties("wx")
@Data
public class WxConfig {

   private String url;

}
