package net.renfei.www.entity.vo;

import lombok.Data;

import java.util.Date;

@Data
public class PostsVO {
    private Long id;
    private Long categoryId;
    private String categoryName;
    private Boolean isOriginal;
    private Boolean isComment;
    private Long views;
    private Date releaseTime;
    private String title;
    private String featuredImage;
    private String content;
    private String sourceUrl;
    private String sourceName;
    private String describes;
    private String keyword;
}
