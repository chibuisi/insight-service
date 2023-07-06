package com.chibuisi.dailyinsightservice.coach.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoachDto {
    private Long userId;
    private String firstname;
    private String lastname;
    private String specialization;
    private String bio;
    private String phone;
    private String availability;
    private String imageLinks;
    private String languages;
    private String certifications;
    private String experience;
    private List<String> topics;
    private LocalDateTime dateBecameCoach;
}
