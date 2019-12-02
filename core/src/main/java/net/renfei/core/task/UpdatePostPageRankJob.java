package net.renfei.core.task;

import lombok.extern.slf4j.Slf4j;
import net.renfei.core.service.PostsService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

@Slf4j
public class UpdatePostPageRankJob extends QuartzJobBean {
    @Autowired
    private PostsService postsService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("== UpdatePostPageRankJob ====");
        postsService.updatePageRank();
        log.info("== UpdatePostPageRankJob ====");
    }
}
