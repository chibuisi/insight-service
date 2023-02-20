package com.chibuisi.dailyinsightservice.topic.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    @Column(unique = true)
    private String name;
    private String description;
    private LocalDateTime dateAdded;
}
