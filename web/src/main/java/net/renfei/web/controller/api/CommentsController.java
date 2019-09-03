package net.renfei.web.controller.api;

import lombok.extern.slf4j.Slf4j;
import net.renfei.core.entity.CommentDTO;
import net.renfei.core.entity.IPDTO;
import net.renfei.core.service.CommentsService;
import net.renfei.core.service.IpService;
import net.renfei.web.baseclass.BaseController;
import net.renfei.web.entity.APIResult;
import net.renfei.web.entity.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/api/comments")
public class CommentsController extends BaseController {

    /**
     * 评论
     *
     * @param typeid  类别ID
     * @param id      被评论的文章ID
     * @param comment 评论实体
     * @return
     */
    @PostMapping("{typeid}/{id}")
    public APIResult submitComments(@PathVariable("typeid") String typeid,
                                    @PathVariable("id") String id,
                                    CommentVO comment) {
        //[TODO]检查全局评论开关
        //[TODO]检查被评论的对象是否允许评论
        Long type = -1L, ID = -1L;
        try {
            type = Long.valueOf(typeid);
            ID = Long.valueOf(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return APIResult.fillResult(false, "Failure of parameter conversion");
        }
        CommentDTO commentDTO = comment.convert();
        commentDTO.setTypeId(type);
        commentDTO.setTargetId(ID);
        commentDTO.setAddtime(new Date());
        commentDTO.setAuthorIp(ipService.getIpAddr(localRequest.get()));
        IPDTO ipdto = ipService.getIPInfor(commentDTO.getAuthorIp());
        if (ipdto != null) {
            commentDTO.setAuthorAddress(ipdto.getCityName() + ", " + ipdto.getRegionName() + ", " + ipdto.getCountryName());
        }
        if (commentsService.submi(commentDTO) == 1) {
            return APIResult.fillResult(true);
        }
        return APIResult.fillResult(false);
    }
}
