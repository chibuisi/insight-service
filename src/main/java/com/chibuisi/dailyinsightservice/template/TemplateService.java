package com.chibuisi.dailyinsightservice.template;

import com.chibuisi.dailyinsightservice.article.model.Article;
import com.chibuisi.dailyinsightservice.article.model.Newsletter;
import com.chibuisi.dailyinsightservice.mail.model.TemplateHelper;
import com.chibuisi.dailyinsightservice.mail.service.MailService;
import com.chibuisi.dailyinsightservice.mail.service.serviceimpl.JavaMailService;
import com.chibuisi.dailyinsightservice.schedules.model.ReadySchedule;
import com.chibuisi.dailyinsightservice.topic.model.SupportedTopics;
import com.chibuisi.dailyinsightservice.topic.model.TopicItem;
import com.chibuisi.dailyinsightservice.topic.service.TopicItemServiceImpl;
import com.chibuisi.dailyinsightservice.user.model.User;
import com.chibuisi.dailyinsightservice.user.service.UserService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;

@Service
public class TemplateService {
    @Autowired
    private TopicItemServiceImpl topicItemService;
    @Autowired
    private Configuration configuration;
    @Autowired
    private UserService userService;
    private MailService mailService;
    public TemplateService(){
        mailService = new JavaMailService();
    }
    public void createTemplate(ReadySchedule readySchedule){
        User user = userService.getUserById(readySchedule.getUserId());
        SupportedTopics topic = SupportedTopics.of(readySchedule.getTopic().getName());
        TopicItem topicItem = topicItemService.findByDateTag();
        Article article = Article.builder()
                .topic(readySchedule.getTopic()).topicItem(topicItem).build();
        Newsletter newsletter = Newsletter.builder()
                .article(article)
                .header(null).footer(null).build();
        String htmlTemplate = "";
        try {
            Template template = configuration
                    .getTemplate(TemplateUtil.getTemplateByTopicName(topic.getName().toLowerCase()));
            htmlTemplate = FreeMarkerTemplateUtils.processTemplateIntoString(template, newsletter);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        TemplateHelper templateHelper = TemplateHelper.builder()
                .htmlTemplate(htmlTemplate).user(user).topic(topic.getName()).build();
        mailService.processTemplate(templateHelper);
    }

}
