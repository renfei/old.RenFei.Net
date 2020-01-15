package net.renfei.core.entity.wechat;

import lombok.Data;

@Data
public class VideoMessage extends WeChatBaseMessage {
    private Video Video;
}
