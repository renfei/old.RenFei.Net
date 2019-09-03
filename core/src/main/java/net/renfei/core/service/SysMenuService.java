package net.renfei.core.service;

import net.renfei.core.baseclass.BaseService;
import net.renfei.core.entity.SysMenuDTO;
import net.renfei.dao.entity.AccountDO;
import net.renfei.dao.entity.SysMenuDOExample;
import net.renfei.dao.entity.SysMenuDOWithBLOBs;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysMenuService extends BaseService {
    public List<SysMenuDTO> getMenuByAccount(AccountDO account) {
        List<SysMenuDTO> menuDTOS = getAllMenu();

        return menuDTOS;
    }

    public List<SysMenuDTO> getAllMenu() {
        SysMenuDOExample sysMenuDOExample = new SysMenuDOExample();
        sysMenuDOExample.setOrderByClause("orderid");
        sysMenuDOExample.createCriteria()
                .andPidEqualTo(0L);
        List<SysMenuDOWithBLOBs> menuDOWithBLOBs = sysMenuDOMapper.selectByExampleWithBLOBs(sysMenuDOExample);
        List<SysMenuDTO> sysMenuDTOS = ejbGenerator.convert(menuDOWithBLOBs, SysMenuDTO.class);
        for (SysMenuDTO menu : sysMenuDTOS
        ) {
            menu.setSubmenu(getSubmenu(menu));
        }
        return sysMenuDTOS;
    }

    private List<SysMenuDTO> getSubmenu(SysMenuDTO menu) {
        SysMenuDOExample sysMenuDOExample = new SysMenuDOExample();
        sysMenuDOExample.setOrderByClause("orderid");
        sysMenuDOExample.createCriteria()
                .andPidEqualTo(menu.getId());
        List<SysMenuDOWithBLOBs> submenuDOWithBLOBs = sysMenuDOMapper.selectByExampleWithBLOBs(sysMenuDOExample);
        if (submenuDOWithBLOBs != null && submenuDOWithBLOBs.size() > 0) {
            List<SysMenuDTO> sysMenuDTOS = ejbGenerator.convert(submenuDOWithBLOBs, SysMenuDTO.class);
            for (SysMenuDTO menu2 : sysMenuDTOS
            ) {
                menu2.setSubmenu(getSubmenu(menu2));
            }
            return sysMenuDTOS;
        } else return null;
    }
}
