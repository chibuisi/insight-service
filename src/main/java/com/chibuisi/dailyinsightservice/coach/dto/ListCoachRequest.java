package com.chibuisi.dailyinsightservice.coach.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListCoachRequest {
    @JsonProperty("token")
    @NotNull(message = "page token must not be null")
    @Digits(integer = 10, fraction = 0, message = "page token must be an integer number - start at 0")
    private String pageToken;
    @JsonProperty("size")
    @Digits(integer = 10, fraction = 0, message = "page size must be an integer number - start at 0")
    private String pageSize;
    @JsonProperty("order")
    @NotNull(message = "order by must not be null")
    //example usage is "order" : "createdDate:desc"
    private String orderBy;
    @JsonProperty("filter")
    @NotNull(message = "filter must not be null")
    //example usage is "filter": ["category=Social Sciences,Physical Sciences","keywords="]
    private List<String> filter;
}
