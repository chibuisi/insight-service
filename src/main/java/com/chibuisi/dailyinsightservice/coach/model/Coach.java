package com.chibuisi.dailyinsightservice.coach.model;

import com.chibuisi.dailyinsightservice.article.model.Article;
import com.chibuisi.dailyinsightservice.topic.model.Topic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Coach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String firstname;
    private String email;
    private String lastname;
    private String specialization;
    private String bio;
    private String phone;
    private String availability;
    private String imageLinks;
    private String languages;
    private String certifications;
    private String experience;
    private Boolean featured;
    private LocalDateTime featuredDate;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "coach_topic",
            joinColumns = @JoinColumn(name = "coach_id"),
            inverseJoinColumns = @JoinColumn(name = "topic_id")
    )
    private List<Topic> topics;
    private Boolean active;
    private LocalDateTime dateBecameCoach;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "coach_article",
            joinColumns = @JoinColumn(name = "coach_id"),
            inverseJoinColumns = @JoinColumn(name = "article_id"))
    private List<Article> articles;
}
