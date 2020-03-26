package net.renfei.core.service;

import lombok.extern.slf4j.Slf4j;
import net.renfei.TestApplication;
import net.renfei.core.entity.CommentDTO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@Slf4j
public class CommentsServiceTest extends TestApplication {
    @Autowired
    private CommentsService commentsService;

    @Test
    public void getComment() {
        List<CommentDTO> commentDTOS = commentsService.getComment(1L, 1L);
        Assert.assertNotNull(commentDTOS);
    }
}
