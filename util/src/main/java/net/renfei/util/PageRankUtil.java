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
        double avgViews = getAvgViews(date, views);
        double avgComments = getAvgComments(date, comments);
        return ((days * dateWeighted) + (avgViews * viewWeighted) + (avgComments * commentWeighted)) / (dateWeighted + viewWeighted + commentWeighted);
    }

    public Double getAvgViews(Date date, Long views) {
        long days = dateUtil.getDifferenceDay(date, new Date());
        return (double) ((float) views / (float) (0 - days));
    }

    public Double getAvgComments(Date date, Long comments) {
        long days = dateUtil.getDifferenceDay(date, new Date());
        return (double) ((float) comments / (float) (0 - days));
    }
}
