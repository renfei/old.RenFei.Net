package net.renfei.web.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class FooterVO {
    private String logo;
    private String logoLink;
    private List<MenuVO> footerMenu;
    private String footerCopy;
    private List<MenuVO> copyList;
    private String analyticsCode;
    private List<String> jss;
}
