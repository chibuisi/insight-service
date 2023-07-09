package com.chibuisi.dailyinsightservice.coach.transformer;

import com.chibuisi.dailyinsightservice.coach.model.Coach;
import com.chibuisi.dailyinsightservice.coach.dto.CoachDto;
import com.chibuisi.dailyinsightservice.topic.model.Topic;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CoachTransformer {

    public static Coach fromDto(CoachDto coachDto) {
        return Coach.builder()
                .firstname(coachDto.getFirstname())
                .lastname(coachDto.getLastname())
                .bio(coachDto.getBio())
                .availability(coachDto.getAvailability())
                .certifications(coachDto.getCertifications())
                .specialization(coachDto.getSpecialization())
                .experience(coachDto.getExperience())
                .phone(coachDto.getPhone())
                .active(Boolean.TRUE)
                .imageLinks(coachDto.getImageLinks())
                .languages(coachDto.getLanguages())
                .userId(coachDto.getUserId())
                .dateBecameCoach(LocalDateTime.now())
                .build();
    }

    public static CoachDto toDto(Coach coach) {

        return CoachDto.builder()
                .firstname(coach.getFirstname())
                .lastname(coach.getFirstname())
                .bio(coach.getBio())
                .certifications(coach.getCertifications())
                .experience(coach.getExperience())
                .availability(coach.getAvailability())
                .imageLinks(coach.getImageLinks())
                .languages(coach.getLanguages())
                .phone(coach.getPhone())
                .userId(coach.getUserId())
                .topics(new ArrayList<>(coach.getTopics().stream().map(Topic::getName).collect(Collectors.toList())))
                .build();
    }

    //do not set active or dateBecameCoach in here
    public static List<Coach> fromDtoList(List<CoachDto> coachDtoList) {
        List<Coach> coaches = new ArrayList<>();
        coachDtoList.forEach(e -> coaches.add(
                Coach.builder()
                        .firstname(e.getFirstname())
                        .lastname(e.getLastname())
                        .phone(e.getPhone())
                        .active(Boolean.FALSE)
                        .build()
        ));
        return coaches;
    }

    public static List<CoachDto> toDtoList(List<Coach> coaches) {
        List<CoachDto> coachDtos = new ArrayList<>();
        coaches.forEach(coach -> coachDtos.add(toDto(coach)));
        return coachDtos;
    }

}
