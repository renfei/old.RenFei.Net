package net.renfei.web.entity;

import lombok.Data;

import java.util.List;

@Data
public class MenuVO {
    private Boolean isNewWin;
    private String menuText;
    private String menuLink;
    private List<MenuVO> child;
}
