package com.chibuisi.dailyinsightservice.topic.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Topic {
    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    //private Integer topicId;
    private String name;
    private String description;
    private LocalDateTime dateAdded;
}
