package com.chibuisi.dailyinsightservice.mail.model;

import com.chibuisi.dailyinsightservice.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TemplateHelper {
    private String htmlTemplate;
    private String topic;
    private User user;
}
