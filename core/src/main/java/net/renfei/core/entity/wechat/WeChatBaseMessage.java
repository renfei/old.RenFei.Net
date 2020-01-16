package net.renfei.core.entity.wechat;

import lombok.Data;

import java.util.Date;

/**
 * 回复微信的消息
 */
@Data
public class WeChatBaseMessage {
    private String ToUserName;
    private String FromUserName;
    private Long CreateTime;
    private String MsgType;
    private String MsgId;

    public WeChatBaseMessage(WeChatMessage weChatMessage,WeChatMessageType weChatMessageType) {
        this.ToUserName = weChatMessage.getFromUserName();
        this.FromUserName = weChatMessage.getToUserName();
        this.CreateTime = new Date().getTime();
        this.MsgType = weChatMessageType.getType();
    }
}
