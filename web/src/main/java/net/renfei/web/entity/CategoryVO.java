package net.renfei.web.entity;

import lombok.Data;
import net.renfei.core.entity.CategoryDTO;

@Data
public class CategoryVO extends CategoryDTO {
    private String href;
}
