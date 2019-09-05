package net.renfei.core.service;

import lombok.extern.slf4j.Slf4j;
import net.renfei.TestApplication;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.*;

@Slf4j
public class MailServiceTest extends TestApplication {
    @Autowired
    private MailService mailService;

    @Test
    public void testSend() {
        log.info("测试邮件发送功能");
//        String to = "16076276@qq.com";
//        List<String> stringList = new ArrayList<>();
//        stringList.add("很高兴能与你取得联系。您的评论留言收到了回复：");
//        stringList.add("这是一封测试邮件。");
//        stringList.add("----");
//        stringList.add("回顾：<a href=\"http://www.renfei.net/post\">这是一个测试邮件</a>");
//        stringList.add("在 2019-08-21 时您写到：");
//        stringList.add("lalalaldg辣的够辣够阿斯顿个");
//        stringList.add("Visit Topic to respond.");
//        mailService.send(to, "RenFei", "模板邮件", stringList);
        log.info("测试邮件发送功能完成");
    }
}
