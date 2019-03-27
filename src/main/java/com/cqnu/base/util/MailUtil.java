package com.cqnu.base.util;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 邮件工具类
 * @Author xzchen
 * @Date 2019/3/8 20:16
 * @Version 1.0
 **/
@Component
public class MailUtil {
    @Autowired
    private JavaMailSender mailSender;

    private static Logger logger = LoggerFactory.getLogger(MailUtil.class);
    /**
     * 邮件发送方
     */
    @Value("${spring.mail.username}")
    private String from;

    /**
     * 模板引擎
     */
    @Autowired
    private FreeMarkerConfigurer configurer;

    /**
     * 发送员工/门店管理员账号信息邮件
     * @param params 模板里对应的参数
     * @param to 收件人
     * @param subject 邮件主题
     * @param templateName 模板名称
     */
    public void sendMail(Object params, String to ,String subject, String templateName){
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            Map<String, Object> model = new HashMap<>();
            model.put("params", params);
            try {
                Template template = configurer.getConfiguration().getTemplate(templateName);
                String text = null;
                try {
                    text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
                    helper.setText(text, true);
                    mailSender.send(message);
                } catch (TemplateException e) {
                    e.printStackTrace();
                }


            } catch (IOException e) {
                e.printStackTrace();
            }


            logger.error("发送邮件成功");
        } catch (MessagingException e) {
            e.printStackTrace();
            logger.error("发送邮件失败"+e);
        }
    }

}
