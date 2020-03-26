package net.renfei.core.service;

import net.renfei.core.baseclass.BaseService;
import net.renfei.core.entity.PageDTO;
import net.renfei.dao.entity.PageDOWithBLOBs;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "PageService")
public class PageService extends BaseService {

    @Cacheable(key = "targetClass+'_'+methodName+'_'+#p0", condition = "#p0!=null")
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

    /**
     * 点赞
     *
     * @param id
     */
    public void thumbsUp(String id) {
        PageDTO pageDTO = getPageByID(id);
        if (pageDTO != null) {
            PageDOWithBLOBs pageDOWithBLOBs = new PageDOWithBLOBs();
            pageDOWithBLOBs.setId(pageDTO.getId());
            pageDOWithBLOBs.setThumbsUp(pageDTO.getThumbsUp() + 1);
            updatePage(pageDOWithBLOBs);
        }
    }

    /**
     * 点踩
     *
     * @param id
     */
    public void thumbsDown(String id) {
        PageDTO pageDTO = getPageByID(id);
        if (pageDTO != null) {
            PageDOWithBLOBs pageDOWithBLOBs = new PageDOWithBLOBs();
            pageDOWithBLOBs.setId(pageDTO.getId());
            pageDOWithBLOBs.setThumbsDown(pageDTO.getThumbsDown() + 1);
            updatePage(pageDOWithBLOBs);
        }
    }

    public int updatePage(PageDOWithBLOBs pageDOWithBLOBs) {
        return pageDOMapper.updateByPrimaryKeySelective(pageDOWithBLOBs);
    }
}
