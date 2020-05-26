package net.renfei.web.entity;

import lombok.Data;

/**
 * <p>Title: LinkVO</p>
 * <p>Description: 链接VO</p>
 *
 * @author RenFei
 * @date : 2020-05-26 09:57
 */
@Data
public class LinkVO {
    private String href;
    private String text;
    private String target;
    private String rel;
    private String style;
}
