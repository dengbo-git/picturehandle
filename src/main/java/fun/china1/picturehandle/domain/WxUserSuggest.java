package fun.china1.picturehandle.domain;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dengbo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("wx_user_suggest")
public class WxUserSuggest {

  /**
   * 自然主键
   */
  @TableField("id")
  private String id;

  /**
   * 建议人的openid
   */
  @TableField("wx_openid")
  private String wxOpenid;
  /**
   * 建议标题
   */
  @TableField("wx_title")
  private String wxTitle;
  /**
   * 建议内容
   */
  @TableField("wx_content")
  private String wxContent;
  /**
   * 联系方式
   */
  @TableField("wx_contact")
  private String wxContact;
  /**
   * 接收管理员的邮箱
   */
  @TableField("wx_receive_email")
  private String wxReceiveEmail;

  /**
   * 邮件发送的状态
   */
  @TableField("wx_receive_state")
  private long wxReceiveState;

  /**
   * 不能发送成功的原因
   */
  @TableField("wx_cannot_reason")
  private String wxCannotReason;

}
