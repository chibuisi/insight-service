package com.chibuisi.dailyinsightservice.mail.service;

import com.chibuisi.dailyinsightservice.mail.model.TemplateHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public interface MailService {
    public void sendMail(MimeMessage mimeMessage);
    public void processTemplate(TemplateHelper templateHelper);
}
