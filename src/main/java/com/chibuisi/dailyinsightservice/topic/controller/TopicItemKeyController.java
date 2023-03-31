package com.chibuisi.dailyinsightservice.topic.controller;

import com.chibuisi.dailyinsightservice.template.TopicItemTemplateService;
import com.chibuisi.dailyinsightservice.topic.model.TopicItemKeyDTO;
import com.chibuisi.dailyinsightservice.topic.service.TopicItemKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/topicitemkey")
public class TopicItemKeyController {
    @Autowired
    private TopicItemKeyService topicItemKeyService;

    @PostMapping
    public ResponseEntity saveTopicItemKey(@RequestBody List<TopicItemKeyDTO> topicItemKeyDTOs){
        return new ResponseEntity(topicItemKeyService.saveTopicItemKeys(topicItemKeyDTOs),
                HttpStatus.OK);
    }
}
