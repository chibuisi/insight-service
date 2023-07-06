package com.chibuisi.dailyinsightservice.topic.service;

import com.chibuisi.dailyinsightservice.topic.dto.TopicListRequest;
import com.chibuisi.dailyinsightservice.topic.dto.TopicListResponse;
import com.chibuisi.dailyinsightservice.topic.dto.TopicRequest;
import com.chibuisi.dailyinsightservice.topic.dto.TopicResponse;
import com.chibuisi.dailyinsightservice.topic.model.Topic;
import com.chibuisi.dailyinsightservice.topic.repository.TopicDao;
import com.chibuisi.dailyinsightservice.topic.repository.TopicRepository;
import com.chibuisi.dailyinsightservice.topic.transformer.TopicTransformer;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TopicService {
    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private TopicDao topicDao;

    public List<TopicResponse> save(List<TopicRequest> topicRequests) {
        //todo implement merge fields
        List<Topic> topics = TopicTransformer.fromRequestDtoList(topicRequests);
        List<String> topicNamesToSave = topics.stream().map(Topic::getName).collect(Collectors.toList());
        List<Topic> existingTopics = topicRepository.findByNameIn(topicNamesToSave);
        topics.removeIf(e -> existingTopics.stream().anyMatch(t -> e.getName().equals(t.getName().toLowerCase())));
        //todo if topics.size() == 0 throw bad request exception
        List<Topic> savedTopics = topicRepository.saveAll(topics);
        return TopicTransformer.fromTopicList(savedTopics);
    }

    public List<TopicResponse> update(List<TopicRequest> topicRequests) {
        //todo implement merge fields
        List<TopicResponse> responseDtos = new ArrayList<>();
        List<Topic> topics = TopicTransformer.fromRequestDtoList(topicRequests);
        topics.forEach(topic -> {
            Optional<Topic> foundTopic = topicRepository.findTopicByName(topic.getName());
            if(foundTopic.isPresent()){
                Topic updatedTopic = TopicTransformer.updateTopic(foundTopic.get(), topic);
                responseDtos.add(TopicTransformer.fromTopic(topicRepository.save(updatedTopic)));
            }
        });

        return  responseDtos;
    }

    public TopicResponse getTopicByIdOrName(Integer topicId, String topicName) {
        //todo implement merge fields
        Optional<Topic> optionalTopic = Optional.empty();
        if(topicId != null)
            optionalTopic = topicRepository.findById(topicId);
        if(topicName != null) {
            optionalTopic = topicRepository.findTopicByName(topicName);
        }
        return optionalTopic.map(TopicTransformer::fromTopic).orElse(null);
    }


    public void deleteByIdOrName(Integer topicId, String topicName) {
        //todo implement merge fields
        Optional<Topic> optionalTopic = Optional.empty();
        if(topicId != null)
            optionalTopic = topicRepository.findById(topicId);
        if(topicName != null) {
            optionalTopic = topicRepository.findTopicByName(topicName);
        }
        optionalTopic.ifPresent(topic -> topicRepository.delete(topic));
    }

    public void setFeatured(List<String> topicNames) {
        //todo implement merge fields
        List<Topic> topics = topicRepository.findByNameIn(topicNames);
        topics.forEach(e -> {
            e.setFeatured(true);
            e.setFeaturedDate(LocalDateTime.now());
        });
        topicRepository.saveAll(topics);
    }

    public List<TopicResponse> getFeaturedTopics(Integer offset, Integer limit) {
        limit = limit > 0 ? limit : 0;
        offset = offset > 0 ? offset : 0;
        Pageable pageable = PageRequest.of(offset, limit, Sort.by("featuredDate").descending());
        Page<Topic> topicPage = topicRepository.findByFeaturedOrderByFeaturedDateDesc(true, pageable);
        return TopicTransformer.fromTopicList(topicPage.getContent());
    }

    public void unFeatureTopics(List<String> topicNames){
        //todo implement merge fields
        List<Topic> topics = topicRepository.findByNameIn(topicNames);
        topics.forEach(e -> e.setFeatured(false));
        topicRepository.saveAll(topics);
    }

    public TopicListResponse list(TopicListRequest request) {
        StringBuilder queryBuilder = new StringBuilder("SELECT t FROM Topic t WHERE 1 = 1");
        Map<String, String> parameters = new HashMap<>();

        if (request.getPageToken() != null && !request.getPageToken().equals("")) {
            queryBuilder.append(" AND t.id > :token");
            parameters.put("token", request.getPageToken());
        }

        if (request.getFilter() != null) {
            request.getFilter().forEach(filter -> {
                String [] filterArr = filter.split("=");
                if(filterArr[0] != null && !filterArr[0].equals("") && filterArr[1] != null && !filterArr[1].equals("")) {
                    switch (filterArr[0]) {
                        case "category":
                            queryBuilder.append(" AND t.category = :category");
                            parameters.put("category", filterArr[1]);
                            break;
                        case "keywords":
                        case "keyword":
                            String [] keywords = filterArr[1].split(",");
                            if(keywords.length == 1) {
                                queryBuilder.append(" AND t.keywords like :keywords");
                                parameters.put("keywords", "%"+filterArr[1]+"%");
                            }
                            else if(keywords.length > 1) {
                                queryBuilder.append(" AND t.keywords like :keywords0");
                                parameters.put("keywords0", "%"+keywords[0]+"%");
                                for(int i = 1; i < keywords.length; i++) {
                                    queryBuilder.append(" OR t.keywords like :keywords").append(i);
                                    parameters.put("keywords"+i, "%"+keywords[i]+"%");
                                }
                            }
                        default:
                    }
                }

            });
        }

        if (request.getOrderBy() != null) {
            String [] sort = request.getOrderBy().split(":");
            if(sort.length > 1 && sort[1].equalsIgnoreCase("desc")) {
                queryBuilder.append(" ORDER BY :sortOrder DESC");
                parameters.put("sortOrder", sort[0]);
            }
            else {
                queryBuilder.append(" ORDER BY :sortOrder ASC");
                parameters.put("sortOrder", request.getOrderBy());
            }
        } else {
            queryBuilder.append(" ORDER BY t.name ASC");
        }

        if (request.getPageSize() != null) {
            queryBuilder.append(" limit :limit");
            parameters.put("limit", request.getPageSize());
        } else {
            queryBuilder.append(" limit 10");
        }

        List<Topic> topics = topicDao.list(queryBuilder.toString(), parameters);
        topics.forEach(topic -> System.out.println(topic.getName()));
        Long totalCount = topicDao.count(queryBuilder.toString(), parameters);
        int pageSize = request.getPageSize() == null ? 0 : Integer.parseInt(request.getPageSize());
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);
        int currentPage = request.getPageToken() == null ? 0 : Integer.parseInt(request.getPageToken());
        int nextPage = currentPage + 1;
        String nextPageToken = nextPage + "/" + totalPages;
        return TopicListResponse.builder()
                .nextPageToken(nextPageToken)
                .topicResponses(TopicTransformer.fromTopicList(topics))
                .build();
    }

//    public void setDefaultFeaturedTopics() {
//        Page<Topic> page =
//                topicRepository.findAll(
//                        PageRequest.of(0, numOfFeaturedTopics, Sort.by(Sort.Order.asc("id")))
//                );
//        Optional<List<Topic>> optionalTopics = Optional.of(page.getContent());
//        if(optionalTopics.isPresent()) {
//            List<Topic> topics = optionalTopics.get();
//            topics.forEach(e -> {
//                e.setFeatured(true);
//                e.setFeaturedDate(LocalDateTime.now());
//            });
//            topicRepository.saveAll(topics);
//        }
//    }
}
