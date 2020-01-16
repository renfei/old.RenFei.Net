package net.renfei.core.entity.wechat;

public enum WeChatMessageType {
    TEXT("text"), IMAGE("image"), VOICE("voice"),
    VIDEO("video"), LINK("link"), LOCATION("location"),
    SHORTVIDEO("shortvideo"), EVENT("event"), MUSIC("music"),
    NEWS("news"), SUBSCRIBE("subscribe"), UNSUBSCRIBE("unsubscribe"),
    SCAN("scan"), CLICK("CLICK"), VIEW("VIEW"), TEMPLATESENDJOBFINISH("TEMPLATESENDJOBFINISH");
    private String type;

    WeChatMessageType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
