package com.chibuisi.dailyinsightservice.coach.service;

import com.chibuisi.dailyinsightservice.coach.dto.CoachResponseDto;
import com.chibuisi.dailyinsightservice.coach.dto.CreateCoachRequest;
import com.chibuisi.dailyinsightservice.coach.dto.UpdateCoachRequest;
import com.chibuisi.dailyinsightservice.coach.model.Coach;
import com.chibuisi.dailyinsightservice.coach.transformer.CoachTransformer;
import com.chibuisi.dailyinsightservice.coach.repository.CoachRepository;
import com.chibuisi.dailyinsightservice.exception.TopicNotFoundException;
import com.chibuisi.dailyinsightservice.exception.UserAlreadyExistException;
import com.chibuisi.dailyinsightservice.exception.UserNotFoundException;
import com.chibuisi.dailyinsightservice.springsecapp.model.AppRole;
import com.chibuisi.dailyinsightservice.springsecapp.model.UserAccount;
import com.chibuisi.dailyinsightservice.springsecapp.service.MyUserDetailsService;
import com.chibuisi.dailyinsightservice.topic.model.Topic;
import com.chibuisi.dailyinsightservice.topic.repository.TopicRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

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



    //requires that user exist
    //should be used only from the apply as coach feature to create coach in db
    //does not and should not set coach as active, only creates a coach
    public CoachResponseDto createCoach(CreateCoachRequest createCoachRequest) {
        if(createCoachRequest == null)
            return null;
        Long id = Long.valueOf(createCoachRequest.getUserId());
        Optional<UserAccount> userAccountOptional = userDetailsService.findUserById(id);
        if(!userAccountOptional.isPresent())
            throw new UserNotFoundException(String.format("User with user id %s not found", id));
        UserAccount userAccount = userAccountOptional.get();
        Optional<Coach> existingCoach = coachRepository.findByUserId(id);
        if(existingCoach.isPresent())
            throw new UserAlreadyExistException("Coach already exist");
        Coach coach = CoachTransformer.fromCreateRequest(createCoachRequest);
        coach.setEmail(userAccount.getEmail());
        Optional<Topic> optionalTopic = topicRepository.findTopicByName(createCoachRequest.getTopic());
        optionalTopic.ifPresent(topic -> {
            List<Topic> topics = new ArrayList<>();
            topics.add(topic);
            coach.setTopics(topics);
        });

        Coach savedCoach = coachRepository.save(coach);

        return CoachTransformer.toCoachResponseDto(savedCoach);
    }

    public CoachResponseDto getCoach(Long userId, String email) {
        Optional<Coach> optionalCoach = Optional.empty();
        if(userId != null)
            optionalCoach = coachRepository.findByUserId(userId);
        if(!optionalCoach.isPresent() && !StringUtils.isBlank(email)) {
            optionalCoach = coachRepository.findCoachByEmail(email);
        }
        if(optionalCoach.isPresent())
            return CoachTransformer.toCoachResponseDto(optionalCoach.get());
        throw new UserNotFoundException("Coach not found");
    }

    public CoachResponseDto updateCoach(UpdateCoachRequest updateCoachRequest) {
        long userId;
        try {
            userId = Long.parseLong(updateCoachRequest.getUserId());
        } catch (Exception e) {
            throw new IllegalArgumentException("userId is invalid");
        }
        Optional<Coach> optionalCoachToUpdate = coachRepository.findByUserId(userId);
        if(!optionalCoachToUpdate.isPresent())
            throw new UserNotFoundException("user id not found");
        Coach coachToUpdate = optionalCoachToUpdate.get();
        Coach updatedCoach = CoachTransformer.applyUpdatesToCoach(coachToUpdate, updateCoachRequest);
        updatedCoach = coachRepository.save(updatedCoach);

        return CoachTransformer.toCoachResponseDto(updatedCoach);
    }

    //assumes that user exist
    //activates a coach
    public void activateCoach(Long userId) {
        Optional<UserAccount> optionalUserAccount = userDetailsService.findUserById(userId);
        if (!optionalUserAccount.isPresent())
            throw new UserNotFoundException("User not found");
        UserAccount userAccount = optionalUserAccount.get();
        //add role_coach and role_can_add_articles, role_can_delete_articles, role_can_modify_articles
        userAccount.getRoles().add(AppRole.builder().build());
        Optional<Coach> optionalCoach = coachRepository.findByUserId(userAccount.getId());
        if(!optionalCoach.isPresent())
            throw new UserNotFoundException("Coach not found");
        Coach coach = optionalCoach.get();
        coach.setActive(Boolean.TRUE);
        coach.setDateActivated(LocalDateTime.now());
        coachRepository.save(coach);
    }

    public void deactivateCoach(Long userId) {
        Optional<UserAccount> optionalUserAccount = userDetailsService.findUserById(userId);
        if (!optionalUserAccount.isPresent())
            throw new UserNotFoundException("User not found");
        UserAccount userAccount = optionalUserAccount.get();
        Optional<Coach> optionalCoach = coachRepository.findByUserId(userAccount.getId());
        if(!optionalCoach.isPresent())
            throw new UserNotFoundException("Coach not found");
        Coach coach = optionalCoach.get();
        coach.setActive(Boolean.FALSE);
        coach.setDateDeactivated(LocalDateTime.now());
        coachRepository.save(coach);
    }

    public void assignTopicToCoach(String topic, Long userId) {
        Optional<UserAccount> userAccount = userDetailsService.findUserById(userId);
        if(!userAccount.isPresent())
            throw new UserNotFoundException(String.format("User with user id %s not found", userId));
        Optional<Coach> existingCoach = coachRepository.findByUserId(userId);
        if(!existingCoach.isPresent())
            throw new UserNotFoundException(String.format("Coach with user id %s not found", userId));
        Coach coach = existingCoach.get();
        Optional<Topic> optionalTopic = topicRepository.findTopicByName(topic);
        if(!optionalTopic.isPresent())
            throw new TopicNotFoundException(String.format("Topic name: %s was not found", topic));
        Topic foundTopic = optionalTopic.get();
        if(coach.getTopics() != null) {
            Topic topic1 = coach.getTopics()
                    .stream()
                    .filter(topic2 -> topic2.getName().equals(optionalTopic.get().getName()))
                    .findFirst()
                    .orElse(null);
            if(topic1 == null)
                coach.getTopics().add(foundTopic);
        }
        else {
            List<Topic> topics = new ArrayList<>();
            topics.add(foundTopic);
            coach.setTopics(topics);
        }
        coachRepository.save(coach);
    }

    public void deAssignTopicFromCoach(String topic, Long userId) {
        Optional<UserAccount> userAccount = userDetailsService.findUserById(userId);
        if(!userAccount.isPresent())
            throw new UserNotFoundException(String.format("User with user id %s not found", userId));
        Optional<Coach> existingCoach = coachRepository.findByUserId(userId);
        if(!existingCoach.isPresent())
            throw new UserNotFoundException(String.format("Coach with user id %s not found", userId));
        Coach coach = existingCoach.get();
        Optional<Topic> optionalTopic = topicRepository.findTopicByName(topic);
        if(!optionalTopic.isPresent())
            throw new TopicNotFoundException(String.format("Topic name: %s was not found", topic));
        if(coach.getTopics() != null) {
            coach.getTopics().removeIf(topic1 -> topic1.getName().equals(optionalTopic.get().getName()));
        }

        coachRepository.save(coach);
    }

    public Map<String, Object> searchCoachByName(String name, int pageNumber, int pageSize) {
        pageSize = Math.max(pageSize, 1);
        pageNumber = Math.max(pageNumber, 0);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("firstname"));
        String firstname = name;
        String lastname = name;
        if(name.contains(" ")) {
            String [] names = name.split(" ");
            firstname = names[0];
            if(names.length > 1)
                lastname = names[1];
        }
        firstname = "%" + firstname + "%";
        lastname = "%" + lastname + "%";
        Page<Coach> coachPage = coachRepository.findAllByFirstnameLikeOrLastnameLikeOrFirstnameLikeOrLastnameLike(
                firstname, lastname, lastname, firstname, pageable);
        return getPageResponseMap(coachPage);
    }

    public Map<String, Object> getActiveCoachesByTopics(List<String> topicNames, int pageNumber, int pageSize) {
        pageSize = Math.max(pageSize, 1);
        pageNumber = Math.max(pageNumber, 0);
        List<Topic> topics = topicRepository.findByNameIn(topicNames);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("firstname"));
        Page<Coach> coachPage = coachRepository.findAllByActiveIsAndTopicsIn(Boolean.TRUE, topics, pageable);
        return getPageResponseMap(coachPage);
    }

    private Map<String, Object> getPageResponseMap(Page<Coach> coachPage) {
        Map<String, Object> result = new HashMap<>();
        if(coachPage.hasContent()) {
            result.put("coaches", CoachTransformer.toCoachResponseDtos(coachPage.getContent()));
        }
        else {
            result.put("coaches", "");
        }
        result.put("page", coachPage.getNumber());
        result.put("size", coachPage.getNumberOfElements());
        result.put("totalSize", coachPage.getTotalElements());
        result.put("totalPages", coachPage.getTotalPages());
        result.put("nextPageNumber", coachPage.hasNext() ? coachPage.nextPageable().getPageNumber() : "");
        return result;
    }

    public List<CoachResponseDto> getActiveFeaturedCoaches(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("featuredDate"));
        Page<Coach> coachPage = coachRepository.findAllByFeaturedIsAndActiveIs(Boolean.TRUE, Boolean.TRUE, pageable);
        List<Coach> coaches = coachPage.getContent();
        return CoachTransformer.toCoachResponseDtos(coaches);
    }

    public Map<String, Object> listActiveCoaches(
            String orderDirection, String orderBy, int pageNumber, int pageSize) {
        Pageable pageable = getPageable(orderDirection, orderBy, pageNumber, pageSize);
        Page<Coach> coachPage = coachRepository.findAllByActiveIs(Boolean.TRUE, pageable);

        return getPageResponseMap(coachPage);
    }

    public Map<String, Object> listAllCoaches(
            String orderDirection, String orderBy, int pageNumber, int pageSize) {
        Pageable pageable = getPageable(orderDirection, orderBy, pageNumber, pageSize);
        Page<Coach> coachPage = coachRepository.findAll(pageable);

        return getPageResponseMap(coachPage);
    }

    private static Pageable getPageable(String orderDirection, String orderBy, int pageNumber, int pageSize) {
        pageSize = Math.max(pageSize, 1);
        pageNumber = Math.max(pageNumber, 0);
        List<String> orderColumns =
                Arrays.asList("firstname", "lastname", "active", "coachApplicationDate","dateActivated", "active");
        orderBy = orderColumns.contains(orderBy) ? orderBy : "firstname";
        orderDirection = orderDirection.equalsIgnoreCase("asc") ? "ASC" : "DESC";
        Sort.Order order = orderDirection.equalsIgnoreCase("asc")
                ? Sort.Order.asc(orderBy)
                : Sort.Order.desc(orderBy);
        return PageRequest.of(pageNumber, pageSize, Sort.by(order));
    }
}

