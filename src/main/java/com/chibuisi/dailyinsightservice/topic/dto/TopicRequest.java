package com.chibuisi.dailyinsightservice.topic.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@JsonIgnoreProperties(ignoreUnknown = true)
public class TopicRequest {
    @NotBlank(message = "name is required")
    @Max(value = 255, message = "name must be a maximum of {value} characters")
    private String name;
    @NotBlank(message = "description is required")
    @Size(min = 40, max = 500, message = "description must be between {min} and {max} characters long.")
    private String description;
    @NotBlank(message = "detail is required")
    @Size(min = 40, max = 2000, message = "detail must be between {min} and {max} characters long.")
    private String detail;
    @Max(value = 255, message = "image url must be a maximum of {value} characters")
    private String imageUrl;
    @Max(value = 1000, message = "keywords must be a maximum of {value} characters")
    private String keywords;
    @Max(value = 255, message = "parent topic must be a maximum of {value} characters")
    private String parentTopic;
    private List<String> relatedTopics;
    @Max(value = 1000, message = "image links must be a maximum of {value} characters")
    private List<String> imageLinks;
    @Max(value = 255, message = "created by must be a maximum of {value} characters")
    private String createdBy;
    @Max(value = 255, message = "category must be a maximum of {value} characters")
    private String category;
}
