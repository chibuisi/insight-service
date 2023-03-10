package com.chibuisi.dailyinsightservice.mail.service.serviceimpl;

import com.chibuisi.dailyinsightservice.mail.model.TemplateHelper;
import com.chibuisi.dailyinsightservice.mail.service.EmailGateway;
import com.chibuisi.dailyinsightservice.mail.service.MailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

public class JavaMailService implements MailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private EmailGateway.EmailOutboundGateway emailOutboundGateway;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    @Async
    public void sendMail(MimeMessage mimeMessage){
        javaMailSender.send(mimeMessage);
    }

    public void processTemplate(TemplateHelper templateHelper){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try{
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            mimeMessageHelper.setTo(templateHelper.getUser().getEmail());
            mimeMessageHelper.setSubject("Hi " + templateHelper.getUser()
                    .getFirstname()+", Your Daily Newsletter is here");
            mimeMessageHelper.addAttachment("logo.png", new ClassPathResource("logo.png"));

            mimeMessageHelper.setText(templateHelper.getHtmlTemplate(), true);
        }
        catch (MessagingException messagingException){
            messagingException.printStackTrace();
        }
        try {
            emailOutboundGateway
                    .sendMailTemplateToPubSub(objectMapper.writeValueAsString(mimeMessage));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
