package net.renfei.web.controller.api;

import lombok.extern.slf4j.Slf4j;
import net.renfei.core.service.TagService;
import net.renfei.dao.entity.TagDO;
import net.renfei.dao.entity.TagRelationDO;
import net.renfei.web.baseclass.BaseRestController;
import net.renfei.web.entity.APIResult;
import net.renfei.web.entity.ElTransferVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/tags")
public class TagsAPIController extends BaseRestController {
    @Autowired
    private TagService tagService;

    @GetMapping("")
    public APIResult getAllTags() {
        try {
            List<TagDO> tagDOS = tagService.getAllTag();
            if(tagDOS!=null){
                List<ElTransferVO> elTransferVOList = new ArrayList<>();
                for (TagDO tag:tagDOS
                     ) {
                    ElTransferVO elTransferVO = new ElTransferVO();
                    elTransferVO.setKey(tag.getId());
                    elTransferVO.setLabel(tag.getZhName());
                    elTransferVO.setDisabled(false);
                    elTransferVOList.add(elTransferVO);
                }
                return APIResult.fillResult(true, "", elTransferVOList);
            }else {
                return APIResult.fillResult(true, "", new ArrayList<>());
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return APIResult.fillResult(false, ex.getMessage());
        }
    }

    @GetMapping("bytargetid")
    public APIResult getTagsByPostId(Long postid) {
        try {
            List<TagDO> tagDOS = tagService.getTagByTargetId(postid);
            if(tagDOS!=null){
                List<ElTransferVO> elTransferVOList = new ArrayList<>();
                for (TagDO tag:tagDOS
                ) {
                    ElTransferVO elTransferVO = new ElTransferVO();
                    elTransferVO.setKey(tag.getId());
                    elTransferVO.setLabel(tag.getZhName());
                    elTransferVO.setDisabled(false);
                    elTransferVOList.add(elTransferVO);
                }
                return APIResult.fillResult(true, "", elTransferVOList);
            }else {
                return APIResult.fillResult(true, "", new ArrayList<>());
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return APIResult.fillResult(false, ex.getMessage());
        }
    }
}
