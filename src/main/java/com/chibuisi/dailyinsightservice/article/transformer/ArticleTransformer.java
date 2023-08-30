package com.chibuisi.dailyinsightservice.article.transformer;

import com.chibuisi.dailyinsightservice.article.dto.ArticleResponseDto;
import com.chibuisi.dailyinsightservice.article.dto.CreateArticleRequest;
import com.chibuisi.dailyinsightservice.article.dto.UpdateArticleRequest;
import com.chibuisi.dailyinsightservice.article.model.Article;
import com.chibuisi.dailyinsightservice.util.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ArticleTransformer {
    public static Article fromCreateArticleRequest(CreateArticleRequest createArticleRequest) {
        return Article.builder()
                .authorUserId(createArticleRequest.getAuthorUserId())
                .content(createArticleRequest.getContent())
                .title(createArticleRequest.getTitle())
                .category(createArticleRequest.getCategory())
                .keywords(String.join(",", createArticleRequest.getKeywords()))
                .tags(String.join(",", createArticleRequest.getTags()))
                .metaDescription(createArticleRequest.getMetaDescription())
                .seoTitle(createArticleRequest.getSeoTitle())
                .summary(createArticleRequest.getSummary())
                .readTime(createArticleRequest.getReadTime())
                .featuredImageLink(createArticleRequest.getFeaturedImageLink())
                .activeStatus(Boolean.FALSE)
                .topicItemProperties(createArticleRequest.getTopicItemProperties())
                .createTime(LocalDateTime.now())
                .lastUpdateTime(LocalDateTime.now())
                .build();
    }

    public static ArticleResponseDto toResponseDto(Article article) {
        if(article == null)
            return null;
        String publicationDate = article.getPublishedDate() == null
                ? null
                : article.getPublishedDate().format(DateUtil.getDateFormat());
        return ArticleResponseDto.builder()
                .id(String.valueOf(article.getId()))
                .authorUserId(String.valueOf(article.getAuthorUserId()))
                .content(article.getContent())
                .title(article.getTitle())
                .summary(article.getSummary())
                .metaDescription(article.getMetaDescription())
                .seoTitle(article.getSeoTitle())
                .category(article.getCategory())
                .readTime(article.getReadTime())
                .readTimes(String.valueOf(article.getReadTimes()))
                .tags(article.getTags())
                .keywords(article.getKeywords())
                .isAdvancedTopic(article.isAdvancedArticle())
                .featuredImageLink(article.getFeaturedImageLink())
                .publicationDate(publicationDate)
                .topicId(String.valueOf(article.getTopicId()))
                .wordCount(String.valueOf(article.getWordCount()))
                .build();
    }

    public static List<ArticleResponseDto> toResponseDtoList(List<Article> articles) {
        List<ArticleResponseDto> articleResponseDtos = new ArrayList<>();
        articles.forEach(article -> {
            articleResponseDtos.add(toResponseDto(article));
        });
        return articleResponseDtos;
    }

    public static Article applyUpdates(UpdateArticleRequest updateArticleRequest, Article article) {
        if (!StringUtils.isBlank(updateArticleRequest.getContent())
                && !updateArticleRequest.getContent().equals(article.getContent())) {
            article.setContent(updateArticleRequest.getContent());
            int wordCount = updateArticleRequest.getContent().split(" ").length;
            article.setWordCount(wordCount);
        }
        //can only change title if new title not on the category
        if (!StringUtils.isBlank(updateArticleRequest.getTitle())
                && !updateArticleRequest.getTitle().equals(article.getTitle()))
            article.setTitle(updateArticleRequest.getTitle());
        if (!StringUtils.isBlank(updateArticleRequest.getSummary())
                && !updateArticleRequest.getSummary().equals(article.getSummary()))
            article.setSummary(updateArticleRequest.getSummary());
        if (!StringUtils.isBlank(updateArticleRequest.getMetaDescription())
                && !updateArticleRequest.getMetaDescription().equals(article.getMetaDescription()))
            article.setMetaDescription(updateArticleRequest.getMetaDescription());
        if (!StringUtils.isBlank(updateArticleRequest.getSeoTitle())
                && !updateArticleRequest.getSeoTitle().equals(article.getSeoTitle()))
            article.setSeoTitle(updateArticleRequest.getSeoTitle());
        if (!StringUtils.isBlank(updateArticleRequest.getFeaturedImageLink())
                && !updateArticleRequest.getFeaturedImageLink().equals(article.getFeaturedImageLink()))
            article.setFeaturedImageLink(updateArticleRequest.getFeaturedImageLink());
        if (!StringUtils.isBlank(updateArticleRequest.getReadTime())
                && !updateArticleRequest.getReadTime().equals(article.getReadTime()))
            article.setReadTime(updateArticleRequest.getReadTime());
        article.setLastUpdateTime(LocalDateTime.now());
        return article;
    }
}
