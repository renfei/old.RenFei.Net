package net.renfei.core.service;

import net.renfei.core.baseclass.BaseService;
import net.renfei.core.entity.PageDTO;
import net.renfei.dao.entity.PageDOWithBLOBs;
import org.springframework.stereotype.Service;

@Service
public class PageService extends BaseService {

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
