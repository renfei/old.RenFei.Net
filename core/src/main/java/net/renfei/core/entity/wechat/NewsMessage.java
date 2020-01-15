package net.renfei.core.entity.wechat;

import lombok.Data;

@Data
public class NewsMessage extends WeChatBaseMessage {
    private String ArticleCount;
    private Articles Articles;
}
