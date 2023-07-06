package com.chibuisi.dailyinsightservice.topic.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TopicRequest {
    @NotBlank(message = "name is required")
    private String name;
    @NotBlank(message = "description is required")
    private String description;
    @NotBlank(message = "detail is required")
    @Size(min = 40, max = 1500, message = "detail must be between {min} and {max} characters long.")
    private String detail;
    private String imageUrl;
    private String keywords;
    private String parentTopic;
    private List<String> relatedTopics;
    private List<String> imageLinks;
    private String createdBy;
    private LocalDateTime createdDate;
}
