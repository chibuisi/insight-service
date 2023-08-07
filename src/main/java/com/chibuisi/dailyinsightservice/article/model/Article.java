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
    @Column(unique = true)
    private String title;
    private Long authorUserId;
    @Column(length = 100000)
    private String content; //add content format
    private LocalDateTime publicationDate;
    @Column(length = 10000)
    private String tags; //todo create object
    @Column(length = 10000)
    private String keywords; //todo create object
    private String dateTag;
    private boolean isAdvancedArticle;
    @Column(length = 10000)
    private String summary;
    @Column(length = 10000)
    private String metaDescription;
    @Column(length = 10000)
    private String seoTitle;
    private String readTime;
    private Long readTimes;
    private Long wordCount;
    @ManyToMany(mappedBy = "articles", fetch = FetchType.LAZY)
    private List<Coach> coaches;
    private Boolean featured;
    private LocalDateTime featuredDate;
    private String featuredImageLink;
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TopicItemProperties> topicItemProperties;
//    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "itemId")
//    private PickOffset pickOffset;
}
