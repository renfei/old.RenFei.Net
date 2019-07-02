package net.renfei.web.entity;

import lombok.Data;

import java.util.Date;

@Data
public class AllInfoVO {
    private Long typeId;
    private String catName;
    private String catEname;
    private Long categoryId;
    private Long id;
    private Date releaseTime;
    private String title;
    private String featuredImage;
    private String describes;

    private String href;
    private String catHref;
}
