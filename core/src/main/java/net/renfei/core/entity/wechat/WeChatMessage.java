package net.renfei.core.entity.wechat;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

import static net.renfei.core.entity.wechat.WeChatMessageType.*;

/**
 * 微信发来的消息
 */
@Data
public class WeChatMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    private String fromUserName;           // 发送发微信账号
    private String toUserName;             // 接收方微信账号
    private String weixinUserName;         // 微信用户名
    private WeChatMessageType messageType;            // 消息类型
    private String message;                // 消息
    private String msgId;
    private WeChatMessageType event;

    public WeChatMessage() {
    }

    public WeChatMessage(Map<String, String> map) {
        this.fromUserName = map.get("FromUserName");
        this.toUserName = map.get("ToUserName");
        this.messageType = valueOf(map.get("MsgType").toUpperCase());
        this.msgId = map.get("MsgId");
        this.event = map.get("Event") == null ? null : valueOf(map.get("Event").toUpperCase());
        this.message = getMessage(map);
    }

    private String getMessage(Map<String, String> map) {
        switch (this.messageType) {
            case TEXT:
                return map.get("Content");
            case IMAGE:
                return map.get("PicUrl");
            case VOICE:
                return map.get("Recognition");
            case VIDEO:
                return "";
            case LOCATION:
                return map.get("Label");
            case LINK:
                return map.get("Url");
            case EVENT:
                return "";
            default:
                return "";
        }
    }
}
