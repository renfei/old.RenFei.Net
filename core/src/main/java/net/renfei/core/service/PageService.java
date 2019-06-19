package net.renfei.core.service;

import net.renfei.core.baseclass.BaseService;
import net.renfei.dao.persistences.PageDOMapper;
import net.renfei.dao.entity.PageDOWithBLOBs;
import net.renfei.core.entity.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PageService extends BaseService {
    @Autowired
    private PageDOMapper pageDOMapper;

    public PageDTO getPageByID(String id) {
        Long ID = 0L;
        if (!stringUtil.isEmpty(id)) {
            try {
                ID = Long.valueOf(id);
                PageDOWithBLOBs pageDOWithBLOBs = pageDOMapper.selectByPrimaryKey(ID);
                return ejbGenerator.convert(pageDOWithBLOBs, PageDTO.class);
            } catch (NumberFormatException nfe) {
                return null;
            }
        } else {
            return null;
        }
    }
}
