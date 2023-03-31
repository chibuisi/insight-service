package com.chibuisi.dailyinsightservice.topic.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
//@Entity
public class PickOffset {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long offsetId;
    //@OneToOne
    private Long itemId;
}
