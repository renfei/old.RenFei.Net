package net.renfei.web.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class PageHeadVO {
    private String sitename;
    private String description;
    private String keywords;
    private OGprotocol opg;
    private List<String> css;
    private List<String> jss;
    private String script;
}
