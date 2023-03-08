package com.chibuisi.dailyinsightservice.template;

import com.chibuisi.dailyinsightservice.mail.service.MailService;
import com.chibuisi.dailyinsightservice.mail.service.serviceimpl.JavaMailService;
import com.chibuisi.dailyinsightservice.schedules.model.ReadySchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemplateService {
    private MailService mailService;
    public TemplateService(){
        mailService = new JavaMailService();
    }
    public void createTemplate(ReadySchedule readySchedule){
        mailService.sendMail();
    }
}
