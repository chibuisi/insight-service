package com.chibuisi.dailyinsightservice.topic.controller;

import com.chibuisi.dailyinsightservice.topic.model.TopicItem;
import com.chibuisi.dailyinsightservice.topic.service.TopicItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topicitem")
public class TopicItemController {
    @Autowired
    private TopicItemService topicItemService;

    @PostMapping
    public ResponseEntity save(@RequestBody TopicItem topicItem){
        return new ResponseEntity(topicItemService.save(topicItem), HttpStatus.OK);
    }

    @GetMapping("/topic/{topicName}")
    public List<TopicItem> getTopicItemsByTopicName(@PathVariable String topicName){
        return topicItemService.getTopicItems(topicName).orElse(null);
    }

    @GetMapping
    public TopicItem getTopicItem(@RequestParam(required = false) String topicItemName,
                                  @RequestParam(required = false) Long topicItemId){
        TopicItem foundTopicItem = null;
        if(topicItemName != null && topicItemName.length() > 0)
            foundTopicItem = topicItemService.get(topicItemName).orElse(null);
        else
            foundTopicItem = topicItemService.get(topicItemId).orElse(null);
        return foundTopicItem;
    }
}
