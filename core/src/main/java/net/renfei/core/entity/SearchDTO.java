package net.renfei.core.entity;

import lombok.Data;
import net.renfei.dao.entity.FullTextIndexDO;

import java.util.List;

@Data
public class SearchDTO {
    private Long total;
    List<FullTextIndexDO> fullTextIndexDOS;
}
