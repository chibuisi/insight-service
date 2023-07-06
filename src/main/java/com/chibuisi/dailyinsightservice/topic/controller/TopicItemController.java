package com.chibuisi.dailyinsightservice.topic.controller;

import com.chibuisi.dailyinsightservice.template.TopicItemTemplateService;
import com.chibuisi.dailyinsightservice.topic.dto.TopicItemResponseDTO;
import com.chibuisi.dailyinsightservice.article.model.Article;
import com.chibuisi.dailyinsightservice.topic.service.TopicItemService;
import com.chibuisi.dailyinsightservice.util.ExcelHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/topicitem")
public class TopicItemController {
    @Autowired
    private TopicItemService topicItemService;
    @Autowired
    private TopicItemTemplateService topicItemTemplateService;

    @PostMapping
    public ResponseEntity save(@RequestBody Article article){
        return new ResponseEntity(topicItemService.save(article), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getTopicItem(@RequestParam String topic,
                                  @RequestParam String title) {
        return new ResponseEntity(topicItemService.getTopicItemByTopicAndTitle(topic, title),
                HttpStatus.OK);
    }

    @PostMapping("/list/{topicName}")
    public ResponseEntity saveTopicItemList(@RequestBody List<Article> articles, @PathVariable String topicName){
        TopicItemResponseDTO responseDTO = topicItemService.saveTopicItemList(articles, topicName);
        if(responseDTO.getDuplicateTopicItems().size() > 0)
            return new ResponseEntity(responseDTO, HttpStatus.CONFLICT);
        return new ResponseEntity(responseDTO, HttpStatus.CREATED);
    }

    // TODO: 3/26/2023 paginate this endpoint
    @GetMapping("/list/topic/{topicName}")
    public ResponseEntity getTopicItemsByTopic(@PathVariable String topicName){
        return new ResponseEntity(topicItemService.getTopicItems(topicName).orElse(null),
                HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity search(@RequestParam(required = false) String title,
                                  @RequestParam(required = false) Long itemId){
        Article foundArticle = null;
        if(title != null && title.length() > 0)
            foundArticle = topicItemService.get(title).orElse(null);
        else
            foundArticle = topicItemService.get(itemId).orElse(null);
        if (foundArticle == null)
            return new ResponseEntity("Not found", HttpStatus.NOT_FOUND);
        return new ResponseEntity(foundArticle, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity updateTopicItem(@RequestBody Article article){
        return new ResponseEntity(topicItemService.update(article), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity deleteTopicItem(@RequestParam String topic,
                                          @RequestParam String title){
        topicItemService.deleteTopicItem(topic, title);
        return new ResponseEntity("Deleted",HttpStatus.OK);
    }

    @GetMapping("/template/download/{topic}")
    public ResponseEntity getFile(@PathVariable String topic) {
        InputStreamResource file = new InputStreamResource(topicItemTemplateService.loadTopicItemKeys(topic));
        String filename = topic+".xlsx";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }

    @PostMapping("/template/upload/{topic}")
    public ResponseEntity saveFile(@RequestParam("file")MultipartFile file, @PathVariable String topic){
        if(!ExcelHelper.hasExcelFormat(file))
            return new ResponseEntity("Upload Valid Excel Format", HttpStatus.BAD_REQUEST);
        TopicItemResponseDTO responseDTO = topicItemService.uploadFile(file, topic);
        if(responseDTO.getDuplicateTopicItems().size() > 0)
            return new ResponseEntity(responseDTO, HttpStatus.CONFLICT);
        return new ResponseEntity(responseDTO, HttpStatus.CREATED);
    }
}
