package net.renfei.core.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import net.renfei.core.baseclass.BaseService;
import net.renfei.core.entity.CommentDTO;
import net.renfei.dao.entity.CommentDOExample;
import net.renfei.dao.entity.CommentDOWithBLOBs;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentsService extends BaseService {

    public int submi(CommentDTO comment) {
        return commentDOMapper.insertSelective(ejbGenerator.convert(comment, CommentDOWithBLOBs.class));
    }

    public List<CommentDTO> getComment(Long typeid, Long id) {
        CommentDOExample commentDOExample = new CommentDOExample();
        commentDOExample.createCriteria()
                .andTypeIdEqualTo(typeid)
                .andTargetIdEqualTo(id)
                .andParentIdIsNull();
        List<CommentDTO> commentDTOS = ejbGenerator.convert(commentDOMapper.selectByExampleWithBLOBs(commentDOExample), CommentDTO.class);
        getCommentByParentID(commentDTOS);
        return commentDTOS;
    }

    public Long getCommentNumber(Long typeId,Long targetId) {
        CommentDOExample commentDOExample = new CommentDOExample();
        commentDOExample.createCriteria()
                .andTypeIdEqualTo(typeId)
                .andTargetIdEqualTo(targetId)
                .andIsDeleteEqualTo(false);
        Page page = PageHelper.startPage(1, 1);
        commentDOMapper.selectByExampleWithBLOBs(commentDOExample);
        return page.getTotal();
    }

    public void getCommentByParentID(List<CommentDTO> commentDTOS) {
        if (commentDTOS != null && commentDTOS.size() > 0) {
            for (CommentDTO commentDTO : commentDTOS
            ) {
                CommentDOExample commentDOExample = new CommentDOExample();
                commentDOExample.createCriteria()
                        .andParentIdEqualTo(commentDTO.getId());
                List<CommentDTO> child = ejbGenerator.convert(commentDOMapper.selectByExampleWithBLOBs(commentDOExample), CommentDTO.class);
                if (child != null && child.size() > 0) {
                    getCommentByParentID(child);
                    commentDTO.setChild(child);
                }
            }
        }
    }
}
