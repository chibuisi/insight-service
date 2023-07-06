package com.chibuisi.dailyinsightservice.template;

import com.chibuisi.dailyinsightservice.article.model.*;
import com.chibuisi.dailyinsightservice.mail.model.TemplateHelper;
import com.chibuisi.dailyinsightservice.mail.service.serviceimpl.JavaMailService;
import com.chibuisi.dailyinsightservice.schedules.model.ReadySchedule;
import com.chibuisi.dailyinsightservice.template.model.ItemKey;
import com.chibuisi.dailyinsightservice.topic.model.SupportedTopic;
import com.chibuisi.dailyinsightservice.topic.model.Topic;
import com.chibuisi.dailyinsightservice.topic.model.TopicItemProperties;
import com.chibuisi.dailyinsightservice.topic.model.UserTopicItemOffset;
import com.chibuisi.dailyinsightservice.topic.service.TopicItemServiceImpl;
import com.chibuisi.dailyinsightservice.topic.service.TopicService;
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
        SupportedTopic topic = SupportedTopic.of(readySchedule.getTopic().getName());
        if(topic == null)
            return;
        Long userTopicItemOffset = getUserTopicItemOffset(user, topic);
        Optional<Article> optionalTopicItem = Optional.empty();
//        if(userTopicItemOffset != null)
//            optionalTopicItem = topicItemService.findTopicItemByOffset(userTopicItemOffset);
        Article article;
        article = optionalTopicItem.orElseGet(() -> topicItemService.findByLatestTag());
        if(article == null)
            article = topicItemService.findByDateTag();
        if(article == null)
            return;
        Newsletter newsletter = Newsletter.builder()
                .article(article).frequencyType(readySchedule.getScheduleType().getValue())
                .header(null).footer(null).build();
        TemplateHelper templateHelper = TemplateHelper.builder()
                .topicItemOffset(userTopicItemOffset)
                .newsletter(newsletter)
                .user(user)
                .readySchedule(readySchedule).build();
        Map<String, Object> model = transformTemplateModel(templateHelper);
        String htmlTemplate;
        try {
            Template template = configuration
                    .getTemplate(TemplateUtil.getTemplateByTopicName(topic.getName().toLowerCase()));
            htmlTemplate = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            templateHelper.setHtmlTemplate(htmlTemplate);
            javaMailService.queueTemplate(templateHelper);
            updateUserTopicItemOffset(user, topic, userTopicItemOffset+1);
            userService.updateUser(user);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    private Long getUserTopicItemOffset(User user, SupportedTopic topic){
        Long offset = user.getUserTopicItemOffsets()
                .stream()
                .filter(e -> e.getUserId().equals(user.getId()) && e.getTopic().equals(topic))
                .findFirst().get().getTopicItemOffset();
        return offset;
    }

    private void updateUserTopicItemOffset(User user, SupportedTopic topic, Long offset){
        if(user == null || user.getUserTopicItemOffsets() == null)
            return;
        Optional<UserTopicItemOffset> existing = user.getUserTopicItemOffsets()
                .stream()
                .filter(e -> e.getUserId().equals(user.getId()) && e.getTopic().equals(topic))
                .findFirst();
        if(!existing.isPresent())
            return;
        user.getUserTopicItemOffsets()
                .stream()
                .filter(e -> e.getUserId().equals(user.getId()) && e.getTopic().equals(topic))
                .findFirst().get().setTopicItemOffset(offset);
    }

    private Map<String, Object> transformTemplateModel(TemplateHelper templateHelper){
        Map<String, Object> model = new HashMap<>();
        Newsletter newsletter = templateHelper.getNewsletter();
        User user = templateHelper.getUser();
        Header header = newsletter.getHeader();
        Footer footer = newsletter.getFooter();
        Article article = newsletter.getArticle();
        List<TopicItemProperties> topicItemProperties = article.getTopicItemProperties();
        Map<String, Object> properties = new HashMap<>();
        ReadySchedule readySchedule = templateHelper.getReadySchedule();
        List<ItemKey> itemKeys = new ArrayList<>();
        model.put("topic", capitaliseFirstLetter(article.getTopicName()));
        model.put("title", capitaliseFirstLetter(article.getTitle()));
        model.put("scheduleType", readySchedule.getScheduleType().getValue().toLowerCase());
        topicItemProperties.forEach(e -> {
            if(!e.getPropertyKey().equals("meaning")) {
                ItemKey itemKey = ItemKey.builder().key(capitaliseFirstLetter(e.getPropertyKey()))
                        .value(capitaliseFirstLetter(e.getPropertyValue())).build();
                itemKeys.add(itemKey);
            }
            properties.put(e.getPropertyKey(), capitaliseFirstLetter(e.getPropertyValue()));
        });
        model.put("itemKeys", itemKeys);
        model.put("properties", properties);
        model.put("article", article);
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
