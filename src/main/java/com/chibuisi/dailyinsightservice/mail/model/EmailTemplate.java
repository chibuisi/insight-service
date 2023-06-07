package com.chibuisi.dailyinsightservice.mail.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class EmailTemplate {
    private String email;
    private String template;
    private String subject;
    private Map<String, Object> additionalData;
}
