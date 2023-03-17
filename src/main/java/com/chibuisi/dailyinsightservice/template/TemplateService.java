package com.chibuisi.dailyinsightservice.template;

import com.chibuisi.dailyinsightservice.article.model.Article;
import com.chibuisi.dailyinsightservice.article.model.Newsletter;
import com.chibuisi.dailyinsightservice.mail.model.TemplateHelper;
import com.chibuisi.dailyinsightservice.mail.service.serviceimpl.JavaMailService;
import com.chibuisi.dailyinsightservice.schedules.model.ReadySchedule;
import com.chibuisi.dailyinsightservice.topic.model.SupportedTopics;
import com.chibuisi.dailyinsightservice.topic.model.TopicItem;
import com.chibuisi.dailyinsightservice.topic.model.UserTopicItemOffset;
import com.chibuisi.dailyinsightservice.topic.service.TopicItemServiceImpl;
import com.chibuisi.dailyinsightservice.user.model.User;
import com.chibuisi.dailyinsightservice.user.service.UserService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Optional;

@Service
public class TemplateService {
    @Autowired
    private TopicItemServiceImpl topicItemService;
    @Autowired
    private Configuration configuration;
    @Autowired
    private UserService userService;
    @Autowired
    private JavaMailService javaMailService;

    @Transactional
    public void createTemplate(ReadySchedule readySchedule){
        User user = userService.getUserById(readySchedule.getUserId());
        if(user == null)
            return;
        SupportedTopics topic = SupportedTopics.of(readySchedule.getTopic().getName());
        if(topic == null)
            return;
        Long userTopicItemOffset = getUserTopicItemOffset(user, topic);
        if(userTopicItemOffset == null)
            return;
        Optional<TopicItem> optionalTopicItem = topicItemService.get(userTopicItemOffset);
        TopicItem.builder().build();
        TopicItem topicItem;
        if(!optionalTopicItem.isPresent())
            topicItem = topicItemService.findByLatestTag();
        else
            topicItem = optionalTopicItem.get();
        if(topicItem == null)
            topicItem = topicItemService.findByDateTag();
        Article article = Article.builder()
                .topic(readySchedule.getTopic()).topicItem(topicItem).build();
        Newsletter newsletter = Newsletter.builder()
                .article(article).frequencyType(readySchedule.getScheduleType().getValue())
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
                .topicItemOffset(userTopicItemOffset)
                .htmlTemplate(htmlTemplate)
                .newsletter(newsletter)
                .user(user)
                .readySchedule(readySchedule).build();
        javaMailService.queueTemplate(templateHelper);
        updateUserTopicItemOffset(user, topic, userTopicItemOffset+1);
        userService.updateUser(user);
    }

    private Long getUserTopicItemOffset(User user, SupportedTopics topic){
        Long offset = user.getUserTopicItemOffsets()
                .stream()
                .filter(e -> e.getUserId().equals(user.getId()))
                .findFirst().get().getTopicItemOffset();
        return offset;
    }

    private User updateUserTopicItemOffset(User user, SupportedTopics topic, Long offset){
        if(user == null || user.getUserTopicItemOffsets() == null)
            return user;
        Optional<UserTopicItemOffset> existing = user.getUserTopicItemOffsets()
                .stream()
                .filter(e -> e.getUserId().equals(user.getId()) && e.getTopic().equals(topic))
                .findFirst();
        if(!existing.isPresent())
            return user;
        user.getUserTopicItemOffsets()
                .stream()
                .filter(e -> e.getUserId().equals(user.getId()) && e.getTopic().equals(topic))
                .findFirst().get().setTopicItemOffset(offset);
        return user;
    }

}
