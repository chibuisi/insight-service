package com.chibuisi.dailyinsightservice.template;

import com.chibuisi.dailyinsightservice.article.model.Article;
import com.chibuisi.dailyinsightservice.article.model.Footer;
import com.chibuisi.dailyinsightservice.article.model.Header;
import com.chibuisi.dailyinsightservice.article.model.Newsletter;
import com.chibuisi.dailyinsightservice.mail.model.TemplateHelper;
import com.chibuisi.dailyinsightservice.mail.service.serviceimpl.JavaMailService;
import com.chibuisi.dailyinsightservice.schedules.model.ReadySchedule;
import com.chibuisi.dailyinsightservice.topic.model.SupportedTopics;
import com.chibuisi.dailyinsightservice.topic.model.TopicItem;
import com.chibuisi.dailyinsightservice.topic.model.TopicItemProperties;
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
import java.util.*;

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
        if(topicItem == null)
            return;
        Article article = Article.builder()
                .topic(readySchedule.getTopic()).topicItem(topicItem).build();
        Newsletter newsletter = Newsletter.builder()
                .article(article).frequencyType(readySchedule.getScheduleType().getValue())
                .header(null).footer(null).build();
        TemplateHelper templateHelper = TemplateHelper.builder()
                .topicItemOffset(userTopicItemOffset)
                .newsletter(newsletter)
                .user(user)
                .readySchedule(readySchedule).build();
        Map<String, Object> model = transformTemplateModel(templateHelper);
        String htmlTemplate = "";
        try {
            Template template = configuration
                    .getTemplate(TemplateUtil.getTemplateByTopicName(topic.getName().toLowerCase()));
            htmlTemplate = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        templateHelper.setHtmlTemplate(htmlTemplate);
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

    private Map<String, Object> transformTemplateModel(TemplateHelper templateHelper){
        Map<String, Object> model = new HashMap<>();
        Newsletter newsletter = templateHelper.getNewsletter();
        User user = templateHelper.getUser();
        Article article = newsletter.getArticle();
        Header header = newsletter.getHeader();
        Footer footer = newsletter.getFooter();
        TopicItem topicItem = article.getTopicItem();
        List<TopicItemProperties> topicItemProperties = topicItem.getTopicItemProperties();
        Map<String, String> properties = new HashMap<>();
        ReadySchedule readySchedule = templateHelper.getReadySchedule();
        model.put("topic", capitaliseFirstLetter(article.getTopic().getName()));
        model.put("title", capitaliseFirstLetter(topicItem.getTitle()));
        model.put("scheduleType", readySchedule.getScheduleType().getValue().toLowerCase());
        topicItemProperties.forEach(e -> {
            properties.put(e.getPropertyKey(), e.getPropertyValue());
        });
        model.put("properties", properties);
        model.put("topicItem", topicItem);
        model.put("firstname", user.getFirstname());
        model.put("email", user.getEmail());
        model.put("header", header);
        model.put("footer", footer);
        return model;
    }

    private String capitaliseFirstLetter(String string){
        if (string == null || string.length() == 0)
            return string;
        string = string.toLowerCase();
        String firstLetter = string.charAt(0)+"";
        firstLetter = firstLetter.toUpperCase();
        string = firstLetter + string.substring(1);
        return string;
    }
}
