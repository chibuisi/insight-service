package com.chibuisi.dailyinsightservice.topic.controller;

import com.chibuisi.dailyinsightservice.topic.dto.TopicListRequest;
import com.chibuisi.dailyinsightservice.topic.dto.TopicRequest;
import com.chibuisi.dailyinsightservice.topic.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/topic")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @GetMapping
    public ResponseEntity<?> getTopic(@RequestParam(required = false) Integer topicId,
                                      @RequestParam(required = false) String topicName){
        return new ResponseEntity<>(topicService.getTopicByIdOrName(topicId,topicName), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_CAN_ADD_TOPIC')")
    public ResponseEntity<?> saveTopic(@Valid @RequestBody TopicRequest topicRequests){
        return new ResponseEntity<>(topicService.save(Collections.singletonList(topicRequests)), HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_CAN_UPDATE_TOPIC')")
    public ResponseEntity<?> updateTopic(@Valid @RequestBody TopicRequest topicRequests){
        return new ResponseEntity<>(topicService.update(Collections.singletonList(topicRequests)), HttpStatus.OK);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_CAN_REMOVE_TOPIC')")
    public ResponseEntity<?> deleteTopic(@RequestParam(required = false) Integer topicId,
                                         @RequestParam(required = false) String topicName){
        topicService.deleteByIdOrName(topicId, topicName);
        return new ResponseEntity<>("Successfully Deleted", HttpStatus.OK);
    }

    @PostMapping("/feature")
    public ResponseEntity<?> featureTopics(@RequestBody List<String> topicNames){
        topicService.setFeatured(topicNames);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @PostMapping("/unfeature")
    public ResponseEntity<?> featureTopic(@RequestBody List<String> topicNames) {
        topicService.unFeatureTopics(topicNames);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @PostMapping("/list")
    public ResponseEntity<?> list(@Valid @RequestBody TopicListRequest topicListRequest) {
        return new ResponseEntity<>(topicService.list(topicListRequest), HttpStatus.OK);
    }
}
