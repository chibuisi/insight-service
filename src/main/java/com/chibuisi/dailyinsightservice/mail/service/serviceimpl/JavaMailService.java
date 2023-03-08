package com.chibuisi.dailyinsightservice.mail.service.serviceimpl;

import com.chibuisi.dailyinsightservice.mail.service.MailService;

public class JavaMailService implements MailService {
    @Override
    public void sendMail() {
        System.out.println("Java mail working");
    }
}
