package net.renfei.core.service;

import lombok.extern.slf4j.Slf4j;
import net.renfei.core.baseclass.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class MailService extends BaseService {
    @Autowired
    protected GlobalService globalService;
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String FROM;
    @Value("${spring.mail.eeply-to}")
    private String REPLYTO;

    /**
     * 邮件发送服务
     *
     * @param to       收件人
     * @param name     收件人昵称
     * @param subject  邮件主题
     * @param contents 邮件内容
     * @return
     */
    public boolean send(String to, String name, String subject, List<String> contents) {
        return send(to, name, subject, contents, null);
    }

    /**
     * 邮件发送服务
     *
     * @param to         收件人
     * @param name       收件人昵称
     * @param subject    邮件主题
     * @param contents   邮件内容
     * @param attachment 附件列表
     * @return
     */
    public boolean send(String to, String name, String subject, List<String> contents, Map<String, File> attachment) {
        String txt = "";
        try {
            Resource resource = new ClassPathResource("templates/layout/email.html");
            txt = fileUtil.readfile(resource.getFile().getPath());
            txt = txt.replace("${NAME}", name);
            txt = txt.replace("${CONTENT}", getContent(contents));
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM y HH:mm:ss 'GMT+8'");
            Date d = new Date();
            String datetime = sdf.format(d);
            txt = txt.replace("${DATETIME}", datetime);
            sdf = new SimpleDateFormat("yyyy");
            String year = sdf.format(d);
            txt = txt.replace("${YEAR}", year);
        } catch (Exception e) {
            txt = getContent(contents);
        }
        //////
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(globalService.getSiteName() + " <" + FROM + ">");
            helper.setReplyTo(name + " <" + REPLYTO + ">");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(txt, true);
            if (attachment != null) {
                for (String key : attachment.keySet()
                ) {
                    FileSystemResource file = new FileSystemResource(attachment.get(key));
                    helper.addInline(key, file);
                }
            }
            mailSender.send(message);
            return true;
        } catch (MessagingException e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    private String getContent(List<String> contents) {
        StringBuilder sb = new StringBuilder();
        for (String s : contents
        ) {
            sb.append("<p>" + s + "</p>");
        }
        return sb.toString();
    }
}
