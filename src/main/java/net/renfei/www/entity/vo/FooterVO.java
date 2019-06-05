package net.renfei.www.entity.vo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Data
@Component
public class FooterVO {
    private String logo;
    private String logoLink;
    private List<Map> footerMenu;
    private String footerCopy;
    private List<Map> copyList;
    private String analyticsCode;
}
