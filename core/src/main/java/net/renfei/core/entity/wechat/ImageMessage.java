package net.renfei.core.entity.wechat;

import lombok.Data;

@Data
public class ImageMessage extends WeChatBaseMessage {
    private Image Image;

    public ImageMessage(WeChatMessage weChatMessage) {
        super(weChatMessage, WeChatMessageType.IMAGE);
    }
}
