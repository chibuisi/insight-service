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
    @Column(length = 500)
    private String email;
    private String lastname;
    @Column(length = 500)
    private String specialization;
    @Column(length = 5000)
    private String bio;
    private String phone;
    @Column(length = 500)
    private String availability;
    @Column(length = 1000)
    private String imageLinks;
    @Column(length = 500)
    private String languages;
    @Column(length = 1000)
    private String certifications;
    @Column(length = 5000)
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
    private LocalDateTime dateActivated;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "coach_article",
            joinColumns = @JoinColumn(name = "coach_id"),
            inverseJoinColumns = @JoinColumn(name = "article_id"))
    private List<Article> articles;
    private LocalDateTime coachApplicationDate;
    private LocalDateTime dateDeactivated;
}
