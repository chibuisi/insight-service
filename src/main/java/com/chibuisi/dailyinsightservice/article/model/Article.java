package com.chibuisi.dailyinsightservice.article.model;

import com.chibuisi.dailyinsightservice.coach.model.Coach;
import com.chibuisi.dailyinsightservice.topic.model.TopicItemProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "article")

public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long topicId;
    private String category;
//    we can have same title in different categories
//    @Column(unique = true)
    private String title;
    private Long authorUserId;
    @Column(columnDefinition = "TEXT")
    private String content; //add content format
    @Column(columnDefinition = "TEXT")
    private String tags; //todo create object
    @Column(columnDefinition = "TEXT")
    private String keywords; //todo create object
    private String dateTag;
    private boolean isAdvancedArticle;
    @Column(columnDefinition = "TEXT")
    private String summary;
    @Column(columnDefinition = "TEXT")
    private String metaDescription;
    @Column(columnDefinition = "TEXT")
    private String seoTitle;
    private String readTime;
    private Long readTimes;
    private Integer wordCount;
    @ManyToMany(mappedBy = "articles", fetch = FetchType.LAZY)
    private List<Coach> coaches;
    private Boolean featured;
    private LocalDateTime featuredDate;
    private LocalDateTime unFeaturedDate;
    private String featuredImageLink;
    private Boolean activeStatus;
    private LocalDateTime publishedDate;
    private LocalDateTime deactivatedDate;
    private LocalDateTime createTime;
    private LocalDateTime lastUpdateTime;
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TopicItemProperties> topicItemProperties;
}
