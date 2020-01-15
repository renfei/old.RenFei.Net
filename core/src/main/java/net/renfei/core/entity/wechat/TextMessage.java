package net.renfei.core.entity.wechat;

import lombok.Data;

@Data
public class TextMessage extends WeChatBaseMessage {
    private String Content;
}
