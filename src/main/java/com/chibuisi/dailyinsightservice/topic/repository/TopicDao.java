package com.chibuisi.dailyinsightservice.topic.repository;

import com.chibuisi.dailyinsightservice.topic.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;

@Component
public class TopicDao {
    @Autowired
    private EntityManager entityManager;

    public List<Topic> list(String query, Map<String, Object> parameters) {
        TypedQuery<Topic> typedQuery = entityManager.createQuery(query, Topic.class);
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            if (entry.getKey().equals("limit")) continue;
            typedQuery.setParameter(entry.getKey(), entry.getValue());
        }
        typedQuery.setMaxResults(Integer.parseInt((String) parameters.get("limit")));
        return typedQuery.getResultList();
    }

    public Long count() {
        String query = "select count(*) from Topic t";
        TypedQuery<Long> typedQueryCount = entityManager.createQuery(query, Long.class);
        return typedQueryCount.getSingleResult();
    }
}
