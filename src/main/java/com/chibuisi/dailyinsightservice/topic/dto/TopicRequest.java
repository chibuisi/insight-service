package com.chibuisi.dailyinsightservice.topic.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@JsonIgnoreProperties(ignoreUnknown = true)
public class TopicRequest {
    @NotBlank(message = "name is required")
    @Size(max = 255, message = "name must be a maximum of {max} characters")
    private String name;
    @NotBlank(message = "description is required")
    @Size(min = 40, max = 500, message = "description must be between {min} and {max} characters long.")
    private String description;
    @NotBlank(message = "detail is required")
    @Size(min = 40, max = 2000, message = "detail must be between {min} and {max} characters long.")
    private String detail;
    @Max(value = 255, message = "image url must be a maximum of {value} characters")
    private String imageUrl;
    @NotNull(message = "Keywords must not be null")
    @Size(max = 1000, message = "keywords must be a maximum of {max} characters")
    private String keywords;
    @Size(max = 255, message = "parent topic must be a maximum of {max} characters")
    private String parentTopic;
    @Valid
    @Size(max = 100, message = "Each related topic must be less than {max} characters")
    private List<String> relatedTopics;
    @Valid
    @Size(max = 255, message = "Each image link must be less than {max} characters")
    private List<String> imageLinks;
    @NotNull(message = "created by must not be null")
    @Size(max = 255, message = "created by must be a maximum of {max} characters")
    private String createdBy;
    @NotNull(message = "category by must not be null")
    @Size(max = 255, message = "category must be a maximum of {max} characters")
    private String category;
}
