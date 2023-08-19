package com.chibuisi.dailyinsightservice.article.service;

import com.chibuisi.dailyinsightservice.article.dto.ArticleResponseDto;
import com.chibuisi.dailyinsightservice.article.dto.CreateArticleRequest;
import com.chibuisi.dailyinsightservice.article.dto.UpdateArticleRequest;
import com.chibuisi.dailyinsightservice.article.model.Article;
import com.chibuisi.dailyinsightservice.article.repository.ArticleRepository;
import com.chibuisi.dailyinsightservice.article.transformer.ArticleTransformer;
import com.chibuisi.dailyinsightservice.exception.ArticleAlreadyExistException;
import com.chibuisi.dailyinsightservice.exception.ArticleNotFoundException;
import com.chibuisi.dailyinsightservice.util.PaginationUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    public ArticleResponseDto createArticle(CreateArticleRequest createArticleRequest) {
        Optional<Article> existingArticleByTitle =
                articleRepository.findArticleByTitleAndCategory(
                        createArticleRequest.getTitle(), createArticleRequest.getCategory());
        if (existingArticleByTitle.isPresent())
            throw new ArticleAlreadyExistException("Article with title and category already exist");
        Article article = ArticleTransformer.fromCreateArticleRequest(createArticleRequest);
        article.setWordCount(createArticleRequest.getContent().split(" ").length);
        article.setPublicationDate(LocalDateTime.now());
        article = articleRepository.save(article);

        return ArticleTransformer.toResponseDto(article);
    }

    public ArticleResponseDto getArticle(Long id) {
        Optional<Article> optionalArticle = articleRepository.findById(id);
        if(!optionalArticle.isPresent())
            throw new ArticleNotFoundException("Article not found");
        return ArticleTransformer.toResponseDto(optionalArticle.get());
    }

    //can be used for list and search
    public Map<String, Object> listArticles(int pageNumber, int pageSize, String filter, String orderBy) {
        Sort sort = PaginationUtil.getSort(orderBy);
        pageNumber = Math.max(pageNumber, 0);
        pageSize = Math.max(pageSize, 1);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        String [] filterArr = getFilter(filter);

        Page<Article> articlesPage;

        if(filterArr[0].equalsIgnoreCase("title"))
            articlesPage = articleRepository.findArticlesByTitleLike(filterArr[1], pageable);
        else if(filterArr[0].equalsIgnoreCase("category"))
            articlesPage = articleRepository.findArticlesByCategoryLike(filterArr[1], pageable);
        else
            articlesPage = articleRepository.findArticlesBy(pageable);

        Map<String, Object> result = new HashMap<>();
        result.put("page", articlesPage.getNumber());
        result.put("size", articlesPage.getTotalElements());
        result.put("total", articlesPage.getTotalPages());
        result.put("next", articlesPage.hasNext() ? articlesPage.nextPageable().getPageNumber() : null);
        result.put("articles", ArticleTransformer.toResponseDtoList(articlesPage.getContent()));

        return result;
    }

    public ArticleResponseDto updateArticle(UpdateArticleRequest updateArticleRequest) {
        //find existing article and verify new title does not exist
        Article article = ArticleTransformer.applyUpdates(updateArticleRequest, Article.builder().build());
        return ArticleTransformer.toResponseDto(article);
    }

    private String[] getFilter(String filter) {
        List<String> validFilters = Arrays.asList("title", "category");
        if(StringUtils.isBlank(filter))
            return new String[]{"title","%%"};
        String [] filterSplit = filter.split(":");
        if(filterSplit.length != 2)
            throw new IllegalArgumentException("filter is invalid");
        if(!validFilters.contains(filterSplit[0]))
            throw new IllegalArgumentException("filter field is not supported");
        filterSplit[1] = "%" + filterSplit[1] + "%";
        return filterSplit;
    }

}
