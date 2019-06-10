package net.renfei.www.entity.vo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class PageHeadVO {
    private String sitename;
    private String description;
    private List<String> css;
    private List<String> jss;
    private String script;
}
