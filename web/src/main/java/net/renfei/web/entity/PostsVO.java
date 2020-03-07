package net.renfei.web.entity;

import lombok.Data;

import java.util.Date;

@Data
public class PostsVO {
    private Long id;
    private Long categoryId;
    private String categoryZhName;
    private String categoryEnName;
    private String categoryTypeName;
    private Boolean isOriginal;
    private Boolean isComment;
    private Long views;
    private Long thumbsUp;
    private Long thumbsDown;
    private Long comments;
    private Date releaseTime;
    private String title;
    private String featuredImage;
    private String content;
    private String sourceUrl;
    private String sourceName;
    private String describes;
    private String keyword;

    public String getDescribes() {
        if (describes != null && describes.length() > 120) {
            return describes.substring(0, 119);
        }
        return describes;
    }
}
