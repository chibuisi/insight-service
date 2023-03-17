package com.chibuisi.dailyinsightservice.mail.service.serviceimpl;

import com.chibuisi.dailyinsightservice.mail.model.SentMail;
import com.chibuisi.dailyinsightservice.mail.model.TemplateHelper;
import com.chibuisi.dailyinsightservice.mail.repository.SentMailRepository;
import com.chibuisi.dailyinsightservice.schedules.model.ReadySchedule;
import com.chibuisi.dailyinsightservice.schedules.model.enums.ReadyScheduleStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SentMailService {
    @Autowired
    private SentMailRepository sentMailRepository;
    public void saveSentMail(SentMail sentMail){
        sentMailRepository.save(sentMail);
    }

    public void saveSentReadySchedule(TemplateHelper templateHelper){
        SentMail sentMail = SentMail.builder()
                .dateSent(LocalDateTime.now())
                .topic(templateHelper.getReadySchedule().getTopic().getName())
                .offset(templateHelper.getTopicItemOffset().toString())
                .email(templateHelper.getUser().getEmail()).build();
        if(templateHelper.isSentStatus())
            sentMail.setStatus(1);
        else
            sentMail.setStatus(0);
        saveSentMail(sentMail);
    }
}
