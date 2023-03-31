package com.chibuisi.dailyinsightservice.scheduler.model;

import com.chibuisi.dailyinsightservice.scheduler.model.enums.WeekOrMonth;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class NewWeekOrMonth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@Column(unique = true)
    private String name;
    private Integer last;
    private Integer current;
}
