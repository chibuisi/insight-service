package com.chibuisi.dailyinsightservice.topic.repository;

import com.chibuisi.dailyinsightservice.topic.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TopicDao {
    @Autowired
    private EntityManager entityManager;

    public List<Topic> list(String query, Map<String, String> parameters) {
//        String jpql = "SELECT t FROM Topic t WHERE 1 = 1";
//        Map<String, Object> parameters = new HashMap<>();
//        TypedQuery<Topic> typedQuery = entityManager.createQuery("SELECT t FROM Topic t WHERE t.name like :name", Topic.class);
        TypedQuery<Topic> typedQuery = entityManager.createQuery(query, Topic.class);
//        typedQuery.setParameter("name", "technology");
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            typedQuery.setParameter(entry.getKey(), entry.getValue());
        }
        return typedQuery.getResultList();
    }

    public Long count(String query, Map<String, String> parameters) {
        TypedQuery<Topic> typedQuery = entityManager.createQuery(query, Topic.class);
        TypedQuery<Long> typedQueryCount = entityManager.createQuery("select count(*) from ("+query+")", Long.class);
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            typedQuery.setParameter(entry.getKey(), entry.getValue());
            typedQueryCount.setParameter(entry.getKey(), entry.getValue());
        }
        return typedQueryCount.getSingleResult();
    }
}
