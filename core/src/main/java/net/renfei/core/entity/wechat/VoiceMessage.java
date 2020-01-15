package net.renfei.core.entity.wechat;

import lombok.Data;

@Data
public class VoiceMessage extends WeChatBaseMessage {
    private Voice Voice;
}
