package net.renfei.core.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import net.renfei.core.baseclass.BaseService;
import net.renfei.core.entity.CommentDTO;
import net.renfei.core.entity.PostsDTO;
import net.renfei.core.entity.TypeDTO;
import net.renfei.core.entity.VideoDTO;
import net.renfei.core.service.aliyun.AliyunGreen;
import net.renfei.dao.entity.CommentDOExample;
import net.renfei.dao.entity.CommentDOWithBLOBs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentsService extends BaseService {
    @Autowired
    private MailService mailService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private GlobalService globalService;
    @Autowired
    private PostsService postsService;
    @Autowired
    private VideoService videoService;
    @Autowired
    private AliyunGreen aliyunGreen;

    public int submi(CommentDTO comment) {
        CommentDOWithBLOBs commentDOWithBLOBs = ejbGenerator.convert(comment, CommentDOWithBLOBs.class);
        commentDOWithBLOBs.setIsDelete(true);
        //核验检查评论内容
        audit(commentDOWithBLOBs);
        int i = commentDOMapper.insertSelective(commentDOWithBLOBs);
        if (i > 0) {
            sendNotify(comment);
        }
        return i;
    }

    public void audit(CommentDOWithBLOBs commentDOWithBLOBs) {
        if (aliyunGreen.textScan(commentDOWithBLOBs.getContent())) {
            commentDOWithBLOBs.setIsDelete(false);
        } else {
            commentDOWithBLOBs.setIsDelete(true);
        }
    }

    public CommentDTO getCommentByID(Long id) {
        CommentDOExample commentDOExample = new CommentDOExample();
        commentDOExample.createCriteria()
                .andIdEqualTo(id);
        List<CommentDOWithBLOBs> commentDOWithBLOBs = commentDOMapper.selectByExampleWithBLOBs(commentDOExample);
        if (commentDOWithBLOBs != null && commentDOWithBLOBs.size() > 0) {
            return ejbGenerator.convert(commentDOWithBLOBs.get(0), CommentDTO.class);
        } else {
            return null;
        }
    }

    public List<CommentDTO> getComment(Long typeid, Long id) {
        CommentDOExample commentDOExample = new CommentDOExample();
        commentDOExample.createCriteria()
                .andTypeIdEqualTo(typeid)
                .andTargetIdEqualTo(id)
                .andIsDeleteEqualTo(false)
                .andParentIdIsNull();
        List<CommentDTO> commentDTOS = ejbGenerator.convert(commentDOMapper.selectByExampleWithBLOBs(commentDOExample), CommentDTO.class);
        getCommentByParentID(commentDTOS);
        return commentDTOS;
    }

    public Long getCommentNumber(Long typeId, Long targetId) {
        CommentDOExample commentDOExample = new CommentDOExample();
        commentDOExample.createCriteria()
                .andTypeIdEqualTo(typeId)
                .andTargetIdEqualTo(targetId)
                .andIsDeleteEqualTo(false);
        Page page = PageHelper.startPage(1, 1);
        commentDOMapper.selectByExampleWithBLOBs(commentDOExample);
        return page.getTotal();
    }

    private void getCommentByParentID(List<CommentDTO> commentDTOS) {
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

    @Async
    public void sendNotify(CommentDTO comment) {
        String sent = "", sentName = "";
        CommentDTO commentDTO = null;
        if (comment.getParentId() == null || comment.getParentId() == 0L) {
            sent = "i@renfei.net";
            sentName = "RenFei";
        } else {
            commentDTO = getCommentByID(comment.getParentId());
            if (commentDTO != null) {
                sent = commentDTO.getAuthorEmail();
                sentName = commentDTO.getAuthor();
            } else {
                return;
            }
        }
        List<String> stringList = new ArrayList<>();
        stringList.add("很高兴能与你取得联系。您的评论留言收到了回复：");
        stringList.add(comment.getContent());
        stringList.add("----");
        String link = globalService.getDomain();
        TypeDTO typeDTO = typeService.getTypeByID(comment.getTypeId());
        if (typeDTO == null) {
            return;
        }
        link += typeDTO.getUriPath() + "/" + comment.getTargetId();
        switch (typeDTO.getTypeName()) {
            case "Posts":
                PostsDTO postsDTO = postsService.getPostsByID(comment.getTargetId().toString(), false);
                stringList.add("回顾：<a href=\"" + link + "\">" + postsDTO.getTitle() + "</a>");
                break;
            case "Video":
                VideoDTO videoDTO = videoService.getVideoByID(comment.getTargetId().toString());
                stringList.add("回顾：<a href=\"" + link + "\">" + videoDTO.getTitle() + "</a>");
                break;
        }

        if (commentDTO != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            stringList.add("在 " + sdf.format(commentDTO.getAddtime()) + " 时您写到：");
            stringList.add(commentDTO.getContent());
        }
        stringList.add("Visit Topic to respond.");
        mailService.send(sent, sentName, "您在[RENFEI.NET]的评论留言有新的回复消息", stringList);
    }
}
