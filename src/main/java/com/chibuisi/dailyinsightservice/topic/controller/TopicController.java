package com.chibuisi.dailyinsightservice.topic.controller;

import com.chibuisi.dailyinsightservice.topic.model.Topic;
import com.chibuisi.dailyinsightservice.topic.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topic")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @GetMapping
    public Topic getTopic(@RequestParam Integer id, @RequestParam String name){
        Topic topic = topicService.get(id);
        if(topic != null)
            return topicService.get(id);
        else
            return topicService.get(name);
    }

    @PostMapping
    public ResponseEntity<?> saveTopic(@RequestBody List<Topic> topics){
        return new ResponseEntity<>(topicService.save(topics), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateTopic(@RequestBody Topic topic){
        return new ResponseEntity<>(topicService.update(topic), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTopic(@RequestParam Integer topicId, @RequestParam String topicName){
        topicService.delete(topicId);
        topicService.delete(topicName);
        return new ResponseEntity<>("Successfully Deleted", HttpStatus.OK);
    }
}
