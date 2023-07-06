package com.chibuisi.dailyinsightservice.article.model;

import com.chibuisi.dailyinsightservice.coach.Coach;
import com.chibuisi.dailyinsightservice.topic.model.SupportedTopic;
import com.chibuisi.dailyinsightservice.topic.model.TopicItemProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
    private String topicName;
    private String category;
    @Column(unique = true)
    private String title;
    private String authorName;
    private String content; //todo create object
    private LocalDateTime publicationDate;
    private String tags; //todo create object
    private String keywords; //todo create object
    private String tag; //todo create object
    private String dateTag;
    private Boolean advancedArticle;
    private Long ownerUserId;
    private String featuredImage;
    private String summary;
    private String metaDescription;
    private String seoTitle;
    private String authorBio;
    private String relatedLinks; //todo create object
    private String comments; //todo create object
    private String readTime;
    private Long numberTimesRead;
    private Long wordCount;
    @ManyToMany(mappedBy = "articles", fetch = FetchType.EAGER)
    private List<Coach> coaches;
    private Boolean featured;
    private LocalDateTime featuredDate;
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TopicItemProperties> topicItemProperties;
//    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "itemId")
//    private PickOffset pickOffset;
}
