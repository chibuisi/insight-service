package com.chibuisi.dailyinsightservice.template;

import com.chibuisi.dailyinsightservice.topic.model.TopicItemKey;
import com.chibuisi.dailyinsightservice.topic.service.TopicItemKeyService;
import com.chibuisi.dailyinsightservice.util.ExcelHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;

@Service
public class TopicItemTemplateService {
    @Autowired
    private TopicItemKeyService topicItemKeyService;

    public ByteArrayInputStream load(String topic) {
        List<TopicItemKey> tutorials = topicItemKeyService.getTopicItemKeys(topic);

        ByteArrayInputStream in = ExcelHelper.topicItemKeysToExcel(tutorials);
        return in;
    }
}
