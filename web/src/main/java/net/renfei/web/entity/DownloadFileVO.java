package net.renfei.web.entity;

import lombok.Data;

import java.util.Date;

@Data
public class DownloadFileVO {
    private Long fileID;
    private String name;
    private String size;
    private Date fileDate;
    private String hash;
    private String fileName;
    private String fileIcon;
    private String baiduYunPanUrl;
    private String baiduYunPanCode;
    private String weChatCode;
}
