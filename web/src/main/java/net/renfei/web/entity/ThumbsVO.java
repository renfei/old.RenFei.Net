package net.renfei.web.entity;

import lombok.Data;

@Data
public class ThumbsVO {
    /**
     * 点踩还是点赞
     */
    private String type;
    /**
     * 系统编号
     */
    private Integer system;
    /**
     * 对象ID
     */
    private String id;
}
