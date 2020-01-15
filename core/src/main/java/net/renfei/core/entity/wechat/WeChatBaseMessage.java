package net.renfei.core.entity.wechat;

import lombok.Data;

@Data
public class WeChatBaseMessage {
    private String ToUserName;
    private String FromUserName;
    private Long CreateTime;
    private String MsgType;
    private String MsgId;
}
