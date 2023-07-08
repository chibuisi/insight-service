package com.chibuisi.dailyinsightservice.topic.service;

import com.chibuisi.dailyinsightservice.topic.dto.ListTopicRequest;
import com.chibuisi.dailyinsightservice.topic.dto.ListTopicResponse;
import com.chibuisi.dailyinsightservice.topic.dto.TopicRequest;
import com.chibuisi.dailyinsightservice.topic.dto.TopicResponse;
import com.chibuisi.dailyinsightservice.topic.model.Topic;
import com.chibuisi.dailyinsightservice.topic.repository.TopicDao;
import com.chibuisi.dailyinsightservice.topic.repository.TopicRepository;
import com.chibuisi.dailyinsightservice.topic.transformer.TopicTransformer;
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
        topics.forEach(topic -> topic.setCreatedDate(LocalDateTime.now()));
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

    public ListTopicResponse list(ListTopicRequest request) {
        StringBuilder queryBuilder = new StringBuilder("SELECT t FROM Topic t WHERE 1 = 1");
        Map<String, Object> parameters = new HashMap<>();
        int pageSizeValue;
        try {
            pageSizeValue = request.getPageSize() == null ? 10 : Integer.parseInt(request.getPageSize());
            if(pageSizeValue < 1)
                pageSizeValue = 10;
        } catch (Exception ex) {
            pageSizeValue = 10;
        }

        if (request.getFilter() != null) {
            request.getFilter().forEach(filter -> {
                String [] filterArr = filter.split("=");
                if(filterArr.length > 1) {
                    switch (filterArr[0]) {
                        case "category":
                            String[] categories = filterArr[1].split(",");
                            queryBuilder.append(" AND (");
                            for (int i = 0; i < categories.length; i++) {
                                queryBuilder.append(" t.category = :").append("category").append(i);
                                parameters.put("category" + i, categories[i]);
                                if (i < categories.length - 1)
                                    queryBuilder.append(" OR ");
                            }
                            queryBuilder.append(")");
                            break;
                        case "keywords":
                        case "keyword":
                            String[] keywords = filterArr[1].split(",");
                            queryBuilder.append(" AND (");
                            for (int i = 0; i < keywords.length; i++) {
                                queryBuilder.append(" t.keywords LIKE :").append("keywords").append(i);
                                parameters.put("keywords" + i, "%" + keywords[i] + "%");
                                if (i < keywords.length - 1)
                                    queryBuilder.append(" OR ");
                            }
                            queryBuilder.append(")");
                            break;
                        default:
                    }
                }
            });
        }

        if (request.getOrderBy() != null) {
            String [] sort = request.getOrderBy().split(":");
            Map<String, String> orderByKeyValuePair = new HashMap<>();
            orderByKeyValuePair.put("createdDate", "createdDate");
            orderByKeyValuePair.put("name", "name");
            orderByKeyValuePair.put("modifiedDate", "modifiedDate");
            orderByKeyValuePair.put("category", "category");
            if(sort.length > 1 && sort[1].equalsIgnoreCase("desc")) {
                if (request.getPageToken() != null && !request.getPageToken().equals("")
                        && Integer.parseInt(request.getPageToken()) != 0) {
                    queryBuilder.append(" AND t.id < :token");
                    parameters.put("token", Integer.parseInt(request.getPageToken()));
                }
                if(orderByKeyValuePair.containsKey(sort[0])) {
                    queryBuilder.append(" ORDER BY t.").append(orderByKeyValuePair.get(sort[0])).append(" DESC");
                }
            }
            else if(orderByKeyValuePair.containsKey(sort[0])) {
                if (request.getPageToken() != null && !request.getPageToken().equals("")
                        && Integer.parseInt(request.getPageToken()) != 0) {
                    queryBuilder.append(" AND t.id > :token");
                    parameters.put("token", Integer.parseInt(request.getPageToken()));
                }
                queryBuilder.append(" ORDER BY t.").append(orderByKeyValuePair.get(sort[0])).append(" ASC");
            }
        }
        else {
            if (request.getPageToken() != null && !request.getPageToken().equals("")
                    && Integer.parseInt(request.getPageToken()) != 0 ){
                queryBuilder.append(" AND t.id > :token");
                parameters.put("token", Integer.parseInt(request.getPageToken()));
            }
            queryBuilder.append(" ORDER BY t.name ASC");
        }

        //put page size in map
        parameters.put("limit", pageSizeValue);

        List<Topic> topics = topicDao.list(queryBuilder.toString(), parameters);
        Integer lastId = 0;
        if(topics.size() == 1)
            lastId = topics.get(0).getId();
        if(topics.size() > 1)
            lastId = topics.get(topics.size() - 1).getId();

        Long totalCount = topicDao.count();
        int totalPages = (int) Math.ceil((double) totalCount / pageSizeValue);
        int currentPage = request.getPageToken() == null ? 1 : Integer.parseInt(request.getPageToken());
        int nextPage = currentPage + 1;

        boolean hasResults = topics.size() > 0;
        return ListTopicResponse.builder()
                .nextPageToken(topics.size() > 0
                        && totalCount > topics.size()
                        && nextPage <= totalCount
                        ? String.valueOf(lastId)
                        : null)
                .pageSize(hasResults ? String.valueOf(pageSizeValue) : null)
                .totalPagesSize(hasResults ? String.valueOf(totalPages) : null)
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
