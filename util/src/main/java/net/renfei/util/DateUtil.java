package net.renfei.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    /**
     * 获取相差的天数
     *
     * @param startDate 开始日期
     * @param endDate   截止日期
     * @return
     */
    public Long getDifferenceDay(Date startDate, Date endDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        long startTimeInMillis = calendar.getTimeInMillis();
        calendar.setTime(endDate);
        long endTimeInMillis = calendar.getTimeInMillis();
        return (startTimeInMillis - endTimeInMillis) / (1000L * 3600L * 24L);
    }
}
