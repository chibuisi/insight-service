package com.chibuisi.dailyinsightservice.mail.model;

import com.chibuisi.dailyinsightservice.article.model.Newsletter;
import com.chibuisi.dailyinsightservice.schedules.model.ReadySchedule;
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
    private Newsletter newsletter;
    private String htmlTemplate;
    private User user;
    private ReadySchedule readySchedule;
    private Long topicItemOffset;
    private boolean sentStatus;
}
