package net.renfei.web.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class HeaderVO {
    private String siteName;
    private String domain;
    private List<MenuVO> menuVOS;
}
