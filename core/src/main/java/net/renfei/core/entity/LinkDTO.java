package net.renfei.core.entity;

import lombok.Data;
import net.renfei.dao.entity.LinkDOWithBLOBs;

import java.util.List;

@Data
public class LinkDTO {
    private List<LinkDOWithBLOBs> links;
}
