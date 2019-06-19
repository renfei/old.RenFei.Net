package net.renfei.core.entity;

import lombok.Data;
import net.renfei.dao.entity.MenuDOWithBLOBs;

import java.util.List;

@Data
public class MenuDTO extends MenuDOWithBLOBs {
    private List<MenuDTO> child;
}
