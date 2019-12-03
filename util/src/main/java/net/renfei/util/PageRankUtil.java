package net.renfei.util;

import java.util.Date;

public class PageRankUtil {
    private DateUtil dateUtil = new DateUtil();

    public Double getPageRank(Date date, Long views, Long comments,
                              Double dateWeighted, Double viewWeighted, Double commentWeighted) {
        long days = dateUtil.getDifferenceDay(date, new Date());
        if (days > -3) {
            return 10000D;
        }
        double avgViews = 0;
        double avgComments = 0;
        avgViews = (float) views / (float) (0 - days);
        avgComments = (float) comments / (float) (0 - days);
        return ((days * dateWeighted) + (avgViews * viewWeighted) + (avgComments * commentWeighted)) / (dateWeighted + viewWeighted + commentWeighted);
    }
}
