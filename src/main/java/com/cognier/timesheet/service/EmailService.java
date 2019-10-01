package com.cognier.timesheet.service;


import com.cognier.timesheet.dto.MailRequest;
import com.cognier.timesheet.dto.MailResponse;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.security.auth.login.Configuration;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.springframework.boot.autoconfigure.*;

@Service
public class EmailService
{
    @Autowired
    private JavaMailSender sender;

    @Autowired
    private Configuration config;

    public MailResponse sendEmail(MailRequest request, Map<String,Object> model)
    {
        MailResponse response=new MailResponse();
        MimeMessage message=sender.createMimeMessage();
        try
        {
            MimeMessageHelper helper=new MimeMessageHelper(message,MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.addAttachment("logo.png",new ClassPathResource("logo.png"));
            Template t=config.getTemplate("email-template.ftl");
            String html= FreeMarkerTemplateUtils.processTemplateIntoString(t,model);

            helper.setTo(request.getTo());
            helper.setText(html,true);
            helper.setSubject(request.getSubject());
            helper.setFrom(response.getSubject);
            helper.setFrom(request.getFrom());
            sender.send(message);

            response.setMessage("mail send to: " + request.getTo());
            response.setStatus(Boolean.TRUE);

        }
        catch (MessagingException | IOException| TemplateException e)
        {
            response.setMessage("Mail Sending Failure: "+ e.getMessage());
        }
        return response;
    }
}
