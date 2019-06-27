package net.renfei.core.service;

import lombok.extern.slf4j.Slf4j;
import net.renfei.core.baseclass.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;

@Slf4j
@Service
public class MailService extends BaseService {
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
     * @param subject  邮件主题
     * @param textHtml 邮件内容
     * @return
     */
    public boolean send(String to, String subject, String textHtml) {
        return send(to, subject, textHtml, null);
    }

    /**
     * 邮件发送服务
     *
     * @param to         收件人
     * @param subject    邮件主题
     * @param textHtml   邮件内容
     * @param attachment 附件列表
     * @return
     */
    public boolean send(String to, String subject, String textHtml, Map<String, File> attachment) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(FROM);
            helper.setReplyTo(REPLYTO);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(textHtml, true);
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
}
