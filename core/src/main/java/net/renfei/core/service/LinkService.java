package net.renfei.core.service;

import net.renfei.core.baseclass.BaseService;
import net.renfei.core.entity.LinkDTO;
import net.renfei.dao.entity.LinkDOExample;
import net.renfei.dao.entity.LinkDOWithBLOBs;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinkService extends BaseService {
    public LinkDTO getLinks(){
        LinkDOExample linkDOExample=new LinkDOExample();
        linkDOExample.createCriteria()
                .andIsDeleteEqualTo(false);
        List<LinkDOWithBLOBs> linkDOWithBLOBs=linkDOMapper.selectByExampleWithBLOBs(linkDOExample);
        if(linkDOWithBLOBs!=null&&linkDOWithBLOBs.size()>0){
            LinkDTO linkDTO = new LinkDTO();
            linkDTO.setLinks(linkDOWithBLOBs);
            return linkDTO;
        }else {
            return null;
        }
    }
}
