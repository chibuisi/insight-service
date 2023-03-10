package com.chibuisi.dailyinsightservice.article.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Newsletter {
    private Article article;
    private Header header;
    private Footer footer;
}
