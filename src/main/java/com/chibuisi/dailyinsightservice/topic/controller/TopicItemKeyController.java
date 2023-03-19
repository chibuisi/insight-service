package com.chibuisi.dailyinsightservice.topic.controller;

import com.chibuisi.dailyinsightservice.template.TopicItemTemplateService;
import com.chibuisi.dailyinsightservice.topic.model.TopicItemKeyDTO;
import com.chibuisi.dailyinsightservice.topic.service.TopicItemKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/topicitemkey")
public class TopicItemKeyController {
    @Autowired
    private TopicItemKeyService topicItemKeyService;
    @Autowired
    private TopicItemTemplateService topicItemTemplateService;

    @PostMapping
    public ResponseEntity saveTopicItemKey(@RequestBody TopicItemKeyDTO topicItemKeyDTO){
        return new ResponseEntity(topicItemKeyService.saveTopicItemKey(topicItemKeyDTO),
                HttpStatus.OK);
    }

    @GetMapping("/template/download/{topic}")
    public ResponseEntity getFile(@PathVariable String topic) {

        InputStreamResource file = new InputStreamResource(topicItemTemplateService.load(topic));
        String filename = topic+".xlsx";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }
}
