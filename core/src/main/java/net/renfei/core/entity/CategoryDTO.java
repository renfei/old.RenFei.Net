package net.renfei.core.entity;

import lombok.Data;
import net.renfei.dao.entity.CategoryDO;

@Data
public class CategoryDTO extends CategoryDO {
    private String typeName;
    private String uriPath;
}
