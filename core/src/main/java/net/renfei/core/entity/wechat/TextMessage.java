package net.renfei.core.entity.wechat;

import lombok.Data;

@Data
public class TextMessage extends WeChatBaseMessage {
    private String Content;

    public TextMessage(WeChatMessage weChatMessage) {
        super(weChatMessage, WeChatMessageType.TEXT);
        this.Content = "";
    }
}
