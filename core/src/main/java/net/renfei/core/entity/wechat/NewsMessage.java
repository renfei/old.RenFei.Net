package net.renfei.core.entity.wechat;

import lombok.Data;

import java.util.List;

@Data
public class NewsMessage extends WeChatBaseMessage {
    private String ArticleCount;
    List<ArticlesItem> Articles;

    public NewsMessage(WeChatMessage weChatMessage) {
        super(weChatMessage, WeChatMessageType.NEWS);
    }
}
