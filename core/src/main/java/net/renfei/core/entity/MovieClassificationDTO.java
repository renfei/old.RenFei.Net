package net.renfei.core.entity;

import lombok.Data;
import net.renfei.dao.entity.CategoryDO;

@Data
public class MovieClassificationDTO extends CategoryDO {
    private Long countMovie;
}
