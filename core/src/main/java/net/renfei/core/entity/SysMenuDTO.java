package net.renfei.core.entity;

import lombok.Data;
import net.renfei.dao.entity.SysMenuDOWithBLOBs;

import java.util.List;

@Data
public class SysMenuDTO extends SysMenuDOWithBLOBs {
    private List<SysMenuDTO> Submenu;
}
