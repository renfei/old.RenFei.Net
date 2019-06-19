package net.renfei.core.service;

import net.renfei.core.baseclass.BaseService;
import net.renfei.dao.persistences.MenuDOMapper;
import net.renfei.dao.entity.MenuDOExample;
import net.renfei.dao.entity.MenuDOWithBLOBs;
import net.renfei.core.entity.MenuDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService extends BaseService {
    private static int HEAD_MENU_TYPE = 1;
    private static int FOOTER_MENU_TYPE = 2;
    private static int FOOTER_COPY_MENU_TYPE = 3;
    @Autowired
    private MenuDOMapper menuDOMapper;

    public List<MenuDTO> getAllHeadMenu() {
        return getMenuByPid(0L, HEAD_MENU_TYPE);
    }

    public List<MenuDTO> getAllFooterMenu() {
        return getMenuByPid(0L, FOOTER_MENU_TYPE);
    }

    public List<MenuDTO> getAllFooterCopyMenu() {
        return getMenuByPid(0L, FOOTER_COPY_MENU_TYPE);
    }

    public List<MenuDTO> getHeadMenuByPid(Long pid) {
        return getMenuByPid(pid, HEAD_MENU_TYPE);
    }

    public List<MenuDTO> getMenuByPid(Long pid, int menuType) {
        List<MenuDTO> menuDTOS = new ArrayList<>();
        MenuDOExample menuDOExample = new MenuDOExample();
        menuDOExample.setOrderByClause("order_number ASC");
        menuDOExample.createCriteria()
                .andMenuTypeEqualTo(menuType)
                .andPidEqualTo(pid)
                .andIsEnableEqualTo(true);
        List<MenuDOWithBLOBs> menuDOWithBLOBs = menuDOMapper.selectByExampleWithBLOBs(menuDOExample);
        for (MenuDOWithBLOBs menu : menuDOWithBLOBs
        ) {
            MenuDTO menuDTO = ejbGenerator.convert(menu, MenuDTO.class);
            List<MenuDTO> childMenuDTO = getMenuByPid(menu.getId(), menuType);
            if (childMenuDTO != null && childMenuDTO.size() > 0) {
                menuDTO.setChild(childMenuDTO);
            }
            menuDTOS.add(menuDTO);
        }
        return menuDTOS;
    }
}
