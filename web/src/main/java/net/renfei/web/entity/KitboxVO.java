package net.renfei.web.entity;

import lombok.Data;

@Data
public class KitboxVO {
    private String icon;
    private String title;
    private String link;
    private String text;

    public KitboxVO(String icon, String title, String text, String link) {
        this.icon = icon;
        this.title = title;
        this.text = text;
        this.link = link;
    }
}
