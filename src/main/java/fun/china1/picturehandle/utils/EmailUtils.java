package fun.china1.picturehandle.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author ：波波
 * @date ：Created in 2022/3/30 21:41
 * @description：邮箱的工具类
 */
@ConfigurationProperties("email")
@Data
public class EmailUtils {

    private String adminReceiveEmail;
    private String sendEmailCount;
    private String sendEmailPassword;


    /**
     * 发送邮件
     * @param title 邮件标题
     * @param content 邮件内容
     * @param contact 发送者的联系方式
     */
    public void sendEmail(String title, String content, String contact) throws MessagingException {

        Properties properties = new Properties();
        // 连接协议
        properties.put("mail.transport.protocol", "smtp");
        // 主机名
        properties.put("mail.smtp.host", "smtp.qq.com");
        // 端口号
        properties.put("mail.smtp.port", 465);
        properties.put("mail.smtp.auth", "true");
        // 设置是否使用ssl安全连接 ---一般都使用
        properties.put("mail.smtp.ssl.enable", "true");
        // 设置是否显示debug信息 true 会在控制台显示相关信息
        properties.put("mail.debug", "true");
        // 得到回话对象
        Session session = Session.getInstance(properties);
        // 获取邮件对象
        Message message = new MimeMessage(session);
        // 设置发件人邮箱地址
        message.setFrom(new InternetAddress("2181779741@qq.com"));
        // 设置收件人邮箱地址
        message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(adminReceiveEmail)});
        //message.setRecipient(Message.RecipientType.TO, new InternetAddress("xxx@qq.com"));//一个收件人
        // 设置邮件标题
        message.setSubject(title);
        // 设置邮件内容
        message.setText(content+"\n\n\n"+(null!=contact?contact:""));
        // 得到邮差对象
        Transport transport = session.getTransport();
        // 连接自己的邮箱账户
        // 密码为QQ邮箱开通的stmp服务后得到的客户端授权码
        transport.connect(sendEmailCount, sendEmailPassword);
        // 发送邮件
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();

    }
}
