package com.chibuisi.dailyinsightservice.coach.transformer;

import com.chibuisi.dailyinsightservice.coach.dto.CoachResponseDto;
import com.chibuisi.dailyinsightservice.coach.dto.CreateCoachRequest;
import com.chibuisi.dailyinsightservice.coach.dto.UpdateCoachRequest;
import com.chibuisi.dailyinsightservice.coach.model.Coach;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CoachTransformer {

    //do not set active or dateBecameCoach in here
    public static Coach fromCreateRequest(CreateCoachRequest createCoachRequest) {
        String availability = createCoachRequest.getAvailability() == null ? "" :
                String.join(",", createCoachRequest.getAvailability());
        String certification = createCoachRequest.getCertifications() == null ? "" :
                String.join(",", createCoachRequest.getCertifications());
        String specialization = createCoachRequest.getSpecialization() == null ? "" :
                String.join(",", createCoachRequest.getSpecialization());
        String imageLinks = createCoachRequest.getImageLinks() == null ? "" :
                String.join(",", createCoachRequest.getImageLinks());
        String languages = createCoachRequest.getLanguages() == null ? "" :
                String.join(",", createCoachRequest.getLanguages());
        return Coach.builder()
                .firstname(createCoachRequest.getFirstname())
                .lastname(createCoachRequest.getLastname())
                .phone(createCoachRequest.getPhone())
                .bio(createCoachRequest.getBio())
                .availability(availability)
                .certifications(certification)
                .specialization(specialization)
                .experience(createCoachRequest.getExperience())
                .phone(createCoachRequest.getPhone())
                .active(Boolean.FALSE)
                .imageLinks(imageLinks)
                .languages(languages)
                .userId(Long.valueOf(createCoachRequest.getUserId()))
                .coachApplicationDate(LocalDateTime.now())
                .build();
    }

    public static List<Coach> fromCreateRequestList(List<CreateCoachRequest> createCoachRequests) {
        List<Coach> coaches = new ArrayList<>();
        createCoachRequests.forEach(createCoachRequest -> coaches.add(fromCreateRequest(createCoachRequest)));
        return coaches;
    }

    public static CoachResponseDto toCoachResponseDto(Coach coach) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateBecameCoach = coach.getDateActivated() == null
                ? null
                : coach.getDateActivated().format(formatter);
        return CoachResponseDto.builder()
                .userId(coach.getUserId())
                .firstname(coach.getFirstname())
                .lastname(coach.getLastname())
                .email(coach.getEmail())
                .bio(coach.getBio())
                .certifications(coach.getCertifications())
                .specialization(coach.getSpecialization())
                .experience(coach.getExperience())
                .phone(coach.getPhone())
                .languages(coach.getLanguages())
                .availability(coach.getAvailability())
                .imageLinks(coach.getImageLinks())
                .dateActivated(dateBecameCoach)
                .build();
    }

    public static List<CoachResponseDto> toCoachResponseDtos(List<Coach> coaches) {
        List<CoachResponseDto> coachResponseDtos = new ArrayList<>();
        coaches.forEach(coach -> coachResponseDtos.add(toCoachResponseDto(coach)));
        return coachResponseDtos;
    }

    public static Coach applyUpdatesToCoach(Coach coachToUpdate, UpdateCoachRequest updateCoachRequest) {
        if(!StringUtils.isBlank(updateCoachRequest.getFirstname()) &&
                !updateCoachRequest.getFirstname().equals(coachToUpdate.getFirstname())) {
            coachToUpdate.setFirstname(updateCoachRequest.getFirstname());
        }
        if(!StringUtils.isBlank(updateCoachRequest.getLastname()) &&
                !updateCoachRequest.getLastname().equals(coachToUpdate.getLastname())) {
            coachToUpdate.setLastname(updateCoachRequest.getLastname());
        }
        if(!StringUtils.isBlank(updateCoachRequest.getBio()) &&
                !updateCoachRequest.getBio().equals(coachToUpdate.getBio())) {
            coachToUpdate.setBio(updateCoachRequest.getBio());
        }
        if(!StringUtils.isBlank(updateCoachRequest.getExperience()) &&
                !updateCoachRequest.getExperience().equals(coachToUpdate.getExperience())) {
            coachToUpdate.setExperience(updateCoachRequest.getExperience());
        }
        if(!StringUtils.isBlank(updateCoachRequest.getPhone()) &&
                !updateCoachRequest.getPhone().equals(coachToUpdate.getPhone())) {
            coachToUpdate.setPhone(updateCoachRequest.getPhone());
        }
        List<String> languagesToUpdate = updateCoachRequest.getLanguages();
        if(languagesToUpdate != null && !languagesToUpdate.isEmpty()) {
            coachToUpdate.setLanguages(String.join(",", languagesToUpdate));
        }
        List<String> availability = updateCoachRequest.getAvailability();
        if(availability != null && !availability.isEmpty()) {
            coachToUpdate.setAvailability(String.join(",", availability));
        }
        List<String> certifications = updateCoachRequest.getCertifications();
        if(certifications != null && !certifications.isEmpty()) {
            coachToUpdate.setCertifications(String.join(",", certifications));
        }
        List<String> specialization = updateCoachRequest.getSpecialization();
        if(specialization != null && !specialization.isEmpty()) {
            coachToUpdate.setSpecialization(String.join(",", specialization));
        }
        List<String> imageLinks = updateCoachRequest.getImageLinks();
        if(imageLinks != null && !imageLinks.isEmpty()) {
            coachToUpdate.setImageLinks(String.join(",", imageLinks));
        }



        return coachToUpdate;
    }

}
