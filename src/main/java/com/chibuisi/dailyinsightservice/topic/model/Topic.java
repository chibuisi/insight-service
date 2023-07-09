package com.chibuisi.dailyinsightservice.topic.model;

import com.chibuisi.dailyinsightservice.article.model.Article;
import com.chibuisi.dailyinsightservice.coach.model.Coach;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String name;
    @Column(length = 500)
    private String description;
    @Column(length = 2000)
    private String detail;
    private String imageUrl;
    private String keywords;
    private String category;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_topic_id")
    private Topic parentTopic;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "topic_related_topics",
            joinColumns = @JoinColumn(name = "topic_id"),
            inverseJoinColumns = @JoinColumn(name = "related_topic_id"))
    private List<Topic> relatedTopics;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "topic_article",
            joinColumns = @JoinColumn(name = "topic_id"),
            inverseJoinColumns = @JoinColumn(name = "article_id"))
    private List<Article> articles;
    private String imageLinks;
    private String createdBy;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private boolean featured;
    private LocalDateTime featuredDate;
    @ManyToMany(mappedBy = "topics", fetch = FetchType.LAZY)
    private List<Coach> coaches;
//    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<TopicItemProperties> topicItemProperties; //todo clear errors associated with this change

}
