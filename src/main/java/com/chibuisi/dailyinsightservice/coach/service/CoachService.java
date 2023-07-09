package com.chibuisi.dailyinsightservice.coach.service;

import com.chibuisi.dailyinsightservice.coach.model.Coach;
import com.chibuisi.dailyinsightservice.coach.transformer.CoachTransformer;
import com.chibuisi.dailyinsightservice.coach.dto.CoachDto;
import com.chibuisi.dailyinsightservice.coach.repository.CoachRepository;
import com.chibuisi.dailyinsightservice.springsecapp.model.UserAccount;
import com.chibuisi.dailyinsightservice.springsecapp.service.MyUserDetailsService;
import com.chibuisi.dailyinsightservice.topic.model.SupportedTopic;
import com.chibuisi.dailyinsightservice.topic.model.Topic;
import com.chibuisi.dailyinsightservice.topic.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
//note you will need to make a user a coach first (ie create a user first)
// and then you can assign topics to the user afterwards
public class CoachService {

    @Autowired
    private CoachRepository coachRepository;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private MyUserDetailsService userDetailsService;

    //assumes that user exist
    //activates a coach
    public List<CoachDto> makeUserCoach(List<Long> userIds) {
        List<UserAccount> userAccounts = userDetailsService.findUsersById(userIds);
        List<Coach> coaches = coachRepository.findAllByIdIn(userAccounts.stream().map(UserAccount::getId).collect(Collectors.toList()));
        if(coaches == null || coaches.size() == 0)
            return null; //todo throw coach not found exception
        coaches.forEach(coach -> {
            coach.setActive(Boolean.TRUE);
            coach.setDateBecameCoach(LocalDateTime.now());
        });
        coaches = coachRepository.saveAll(coaches);
        return CoachTransformer.toDtoList(coaches);
    }

    //requires that user exist
    //should be used only from the apply as coach endpoint
    //does not and should not set coach as active, only creates a coach
    public List<CoachDto> saveCoach(List<CoachDto> coachDtoList) {
        if(coachDtoList == null)
            return null;
        coachDtoList.removeIf(e -> e.getUserId() == null);
        List<Long> userIds = coachDtoList.stream().map(CoachDto::getUserId).collect(Collectors.toList());
        coachDtoList.removeIf(e -> !userIds.contains(e.getUserId()));
        List<Coach> coachToSave = CoachTransformer.fromDtoList(coachDtoList);
        List<Coach> savedCoaches = coachRepository.saveAll(coachToSave);
        return CoachTransformer.toDtoList(savedCoaches);
    }

    public CoachDto getActiveCoach(Long id, Long userId) {
        Optional<Coach> optionalCoach = coachRepository.findByIdAndActiveIs(id, Boolean.TRUE);
        if(!optionalCoach.isPresent()) {
            optionalCoach = coachRepository.findByUserIdAndActiveIs(userId, Boolean.TRUE);
        }
        if(!optionalCoach.isPresent()) {
            //todo throw coach not found exception
            return null;
        }
        Coach coach = optionalCoach.get();
        return CoachTransformer.toDto(coach);
    }

    public void deactivateCoach(Long id, Long userId) {
        Optional<Coach> optionalCoach = coachRepository.findById(id);
        if(optionalCoach.isPresent()) {
            Coach coach = optionalCoach.get();
            coach.setActive(Boolean.FALSE);
            coachRepository.save(coach);
        } else {
            optionalCoach = coachRepository.findByUserId(userId);
            if(optionalCoach.isPresent()) {
                Coach coach = optionalCoach.get();
                coach.setActive(Boolean.FALSE);
                coachRepository.save(coach);
            }
        }
    }

    public List<CoachDto> searchActiveCoachByName(String firstname, String lastname, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("firstname").ascending());
        Page<Coach> coachPage = coachRepository.findAllByFirstnameAndLastnameAndActiveIs(firstname, lastname, Boolean.TRUE, pageable);
        List<Coach> coaches = coachPage.getContent();
        return CoachTransformer.toDtoList(coaches);
    }

    public List<CoachDto> searchActiveCoachBySpecialization(String specialization, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("firstname").ascending());
        Page<Coach> coachPage = coachRepository.findAllBySpecializationAndActiveIs(specialization, Boolean.TRUE, pageable);
        List<Coach> coaches = coachPage.getContent();
        return CoachTransformer.toDtoList(coaches);
    }

    public List<CoachDto> searchActiveCoachByCertification(String certifcation, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("firstname").ascending());
        Page<Coach> coachPage = coachRepository.findAllByCertificationsLikeAndActiveIs(certifcation, Boolean.TRUE, pageable);
        List<Coach> coaches = coachPage.getContent();
        return CoachTransformer.toDtoList(coaches);
    }

    public List<CoachDto> getActiveCoachesByTopicName(List<String> topicNames, int pageNumber, int pageSize) {
        List<SupportedTopic> supportedTopics = SupportedTopic.fromStringList(topicNames);
        List<String> supportedTopicNames = supportedTopics.stream().map(SupportedTopic::getName).collect(Collectors.toList());
        List<Topic> topics = topicRepository.findByNameIn(supportedTopicNames);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Coach> coachesPage = coachRepository.findAllByActiveIsAndTopicsIn(Boolean.TRUE, topics, pageable);
        return CoachTransformer.toDtoList(coachesPage.getContent());
    }

    public CoachDto assignTopicToActiveCoach(Long id, List<String> topicNames) {
        //todo throw exception if id is null
        List<SupportedTopic> supportedTopics = SupportedTopic.fromStringList(topicNames);
        List<String> supportedTopicNames = supportedTopics.stream().map(SupportedTopic::getName).collect(Collectors.toList());
        List<Topic> topics = topicRepository.findByNameIn(supportedTopicNames);
        Optional<Coach> optionalCoach = coachRepository.findByIdAndActiveIs(id, Boolean.TRUE);
        if(optionalCoach.isPresent()) {
            Coach coach = optionalCoach.get();
            coach.setTopics(topics);
            coachRepository.save(coach);
            return CoachTransformer.toDto(coach);
        }
        return null;
    }

    public List<CoachDto> getActiveFeaturedCoaches(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("featuredDate").descending());
        Page<Coach> coachPage = coachRepository.findAllByFeaturedIsAndActiveIs(Boolean.TRUE, Boolean.TRUE, pageable);
        List<Coach> coaches = coachPage.getContent();
        return CoachTransformer.toDtoList(coaches);
    }
}

