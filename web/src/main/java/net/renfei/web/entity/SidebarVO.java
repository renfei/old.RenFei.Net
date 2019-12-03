package net.renfei.web.entity;

import lombok.Data;
import net.renfei.dao.entity.TagDOExtend;

import java.util.List;

@Data
public class SidebarVO {
    private ITellYou iTellYou;
    private List<CategoriesVo> categories;
    private List<TagDOExtend> tags;
    private String staticdomain;
}
