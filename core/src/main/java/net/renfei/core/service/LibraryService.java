package net.renfei.core.service;

import net.renfei.core.baseclass.BaseService;
import net.renfei.core.entity.MenuDTO;
import net.renfei.dao.entity.LibraryDO;
import net.renfei.dao.entity.LibraryDOExample;
import net.renfei.dao.entity.LibraryDetailsDOExample;
import net.renfei.dao.entity.LibraryDetailsDOWithBLOBs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LibraryService extends BaseService {
    @Autowired
    private MenuService menuService;

    public List<LibraryDetailsDOWithBLOBs> getLibraryDetails(Long libraryid) {
        LibraryDetailsDOExample libraryDetailsDOExample = new LibraryDetailsDOExample();
        libraryDetailsDOExample.createCriteria()
                .andLibraryIdEqualTo(libraryid);
        return libraryDetailsDOMapper.selectByExampleWithBLOBs(libraryDetailsDOExample);
    }

    public LibraryDO getLibraryByName(String name) {
        LibraryDOExample libraryDOExample = new LibraryDOExample();
        libraryDOExample.createCriteria()
                .andResourceNameEqualTo(name);
        List<LibraryDO> libraryDOS = libraryDOMapper.selectByExample(libraryDOExample);
        if (libraryDOS != null && libraryDOS.size() > 0) {
            return libraryDOS.get(0);
        }
        return null;
    }

    public List<MenuDTO> getLibraryDirectoryJson() {
        List<MenuDTO> menuDTOS = menuService.getMenuByPid(0L, 10);
        addLib(menuDTOS);
        return menuDTOS;
    }

    private void addLib(List<MenuDTO> menuDTOS) {
        for (MenuDTO menu : menuDTOS
        ) {
            if (menu.getId() != null) {
                List<LibraryDO> libraryDOS = getLibByMenuId(menu.getId());
                if (libraryDOS != null && libraryDOS.size() > 0) {
                    List<MenuDTO> menuDTOS1 = new ArrayList<>();
                    for (LibraryDO lib : libraryDOS
                    ) {
                        MenuDTO menuDTO = new MenuDTO();
                        menuDTO.setMenuText(lib.getResourceName());
                        menuDTO.setMenuLink(lib.getResourceName());
                        if (menu.getChild() == null) {
                            menu.setChild(menuDTOS1);
                        }
                        menu.getChild().add(menuDTO);
                    }
                }
                if (menu.getChild() != null && menu.getChild().size() > 0 && menu.getChild().get(0).getId() != null) {
                    addLib(menu.getChild());
                }
            }
        }
    }

    private List<LibraryDO> getLibByMenuId(Long Id) {
        LibraryDOExample libraryDOExample = new LibraryDOExample();
        libraryDOExample.createCriteria()
                .andMenuIdEqualTo(Id);
        return libraryDOMapper.selectByExample(libraryDOExample);
    }
}
