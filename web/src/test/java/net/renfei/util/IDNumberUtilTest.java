package net.renfei.util;

import lombok.extern.slf4j.Slf4j;
import net.renfei.TestApplication;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
public class IDNumberUtilTest extends TestApplication {
    @Test
    public void idNumberTest() throws ParseException {
        String idPre = "6221261980", idPost = "314", idCheck = "4";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = dateFormat.parse("1980-01-01");
        List<String> allID = new ArrayList<>();
        for (; ; ) {
            Calendar c = Calendar.getInstance();
            c.setTime(birthDate);
            c.add(Calendar.DAY_OF_MONTH, 1);// 今天+1天
            birthDate = c.getTime();
            if (c.get(Calendar.YEAR) == 1981) {
                break;
            }
            int month = c.get(Calendar.MONTH) + 1;
            int day = c.get(Calendar.DAY_OF_MONTH);
            allID.add(idPre + (month < 10 ? "0" + month : month) + (day < 10 ? "0" + day : day) + idPost);
        }
        IDNumberUtil idNumberUtil = new IDNumberUtil();
        List<String> suspectedID = new ArrayList<>();
        for (String id : allID
        ) {
            log.info("== 检查：{}", id + idCheck);
            if (idCheck.equals(idNumberUtil.getValidateCode(id) + "")) {
                suspectedID.add(id + idCheck);
            }
        }
        for (String id : suspectedID
        ) {
            log.info("== 发现疑似ID：{}", id);
        }
    }
}
