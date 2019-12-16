package net.renfei.web.entity;

import lombok.Data;

@Data
public class FriendLinkVO {
    private Integer linkType;
    private String contactEmail;
    private String contactQq;
    private String sitename;
    private String sitelink;
    private String inSiteLink;
    private String contactName;
    private String remarks;
}
