package net.renfei.core.entity.wechat;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Component
public class WeChatMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    private String fromUserName;           // 发送发微信账号
    private String toUserName;             // 接收方微信账号
    private String weixinUserName;         // 微信用户名
    private String messageType;            // 消息类型

    @Override
    public String toString() {
        return "WeChatMessage [fromUserName=" + fromUserName
                + ", toUserName=" + toUserName + ", weixinUserName="
                + weixinUserName + ", messageType=" + messageType + "]";
    }
}
