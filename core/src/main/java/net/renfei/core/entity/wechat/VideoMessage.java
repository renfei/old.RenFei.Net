package net.renfei.core.entity.wechat;

import lombok.Data;

@Data
public class VideoMessage extends WeChatBaseMessage {
    private Video Video;

    public VideoMessage(WeChatMessage weChatMessage) {
        super(weChatMessage, WeChatMessageType.VIDEO);
    }
}
