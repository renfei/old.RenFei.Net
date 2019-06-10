package net.renfei.www.entity.vo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class HeaderVO {
    private String siteName;
    private String domain;
}
