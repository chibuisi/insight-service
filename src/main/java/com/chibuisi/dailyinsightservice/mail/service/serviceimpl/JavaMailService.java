package com.chibuisi.dailyinsightservice.mail.service.serviceimpl;

import com.chibuisi.dailyinsightservice.mail.model.EmailTemplate;
import com.chibuisi.dailyinsightservice.mail.model.TemplateHelper;
import com.chibuisi.dailyinsightservice.pubsub.service.PubSubMessagingGateways;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
public class JavaMailService {

    private static final String SENDER = "Minor Insights <chibuisi.test.dev@gmail.com>";
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private PubSubMessagingGateways.PubSubOutboundGateway pubSubOutboundGateway;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SentMailService sentMailService;
    @Value("${spring.application.name}")
    private String appName;

    @Async("sendMailThreadPoolTaskExecutor")
    public void sendMail(TemplateHelper templateHelper){
        String email = templateHelper.getUser().getEmail();
        System.out.println("Sending to: "+ email +", Thread "+ Thread.currentThread().getName());
        MimeMessage mimeMessage = getMimeMessage(templateHelper);
        try{
            javaMailSender.send(mimeMessage);
            templateHelper.setSentStatus(true);
            sentMailService.saveSentReadySchedule(templateHelper);
        }catch (Exception e){
            templateHelper.setSentStatus(false);
            sentMailService.saveSentReadySchedule(templateHelper);
        }
    }

    private MimeMessage getMimeMessage(TemplateHelper templateHelper){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try{
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            mimeMessageHelper.setTo(templateHelper.getUser().getEmail());
            mimeMessageHelper.setFrom(SENDER);
            mimeMessageHelper.setSubject("Stay Informed With Insights");
            //mimeMessageHelper.addAttachment("logo.png", new ClassPathResource("logo.png"));
            mimeMessageHelper.setText(templateHelper.getHtmlTemplate(), true);
        }
        catch (MessagingException messagingException){
            messagingException.printStackTrace();
        }
        return mimeMessage;
    }

    public void queueTemplate(TemplateHelper templateHelper){
        try {
            pubSubOutboundGateway
                    .sendMailTemplateToPubSub(objectMapper.writeValueAsString(templateHelper));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void sendEmail(EmailTemplate emailTemplate) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try{
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            mimeMessageHelper.setTo(emailTemplate.getEmail());
            mimeMessageHelper.setFrom(SENDER);
            mimeMessageHelper.setSubject(emailTemplate.getSubject());
            //mimeMessageHelper.addAttachment("logo.png", new ClassPathResource("logo.png"));
            mimeMessageHelper.setText(emailTemplate.getTemplate(), true);
        }
        catch (MessagingException messagingException){
            messagingException.printStackTrace();
        }
        try{
            javaMailSender.send(mimeMessage);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
