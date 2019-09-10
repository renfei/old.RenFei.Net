package net.renfei.web.entity;

import lombok.Data;

import java.util.List;

@Data
public class SidebarVO {
    private ITellYou iTellYou;
    private List<CategoriesVo> categories;
    private List<String> tags;
    private String staticdomain;
}
