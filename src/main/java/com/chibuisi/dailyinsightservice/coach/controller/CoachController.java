package com.chibuisi.dailyinsightservice.coach.controller;

import com.chibuisi.dailyinsightservice.coach.dto.CreateCoachRequest;
import com.chibuisi.dailyinsightservice.coach.dto.UpdateCoachRequest;
import com.chibuisi.dailyinsightservice.coach.service.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import java.util.List;

@RestController
@RequestMapping("/coach")
public class CoachController {

    @Autowired
    private CoachService coachService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> saveCoach(@Valid @RequestBody CreateCoachRequest createCoachRequest) {
        return new ResponseEntity<>(coachService.createCoach(createCoachRequest),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getCoach(@RequestParam(required = false)
                                          @Digits(integer = 10, fraction = 0, message = "user id must have a valid value")
                                          Long userId,
                                      @RequestParam(required = false) String email) {
        return new ResponseEntity<>(coachService.getCoach(userId, email), HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_COACH')")
    public ResponseEntity<?> updateCoach(@Valid @RequestBody UpdateCoachRequest updateCoachRequest) {
        return new ResponseEntity<>(coachService.updateCoach(updateCoachRequest), HttpStatus.OK);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_CAN_REMOVE_COACH')")
    public ResponseEntity<?> deleteCoach(@RequestParam(required = false) Integer userId,
                                         @RequestParam(required = false) String email){
        return new ResponseEntity<>("Successfully Deleted", HttpStatus.OK);
    }

    @PutMapping("/activate")
    @PreAuthorize("hasRole('ROLE_CAN_MANAGE_COACH')")
    public ResponseEntity<?> activateCoach(@RequestParam Long userId) {
        coachService.activateCoach(userId);
        return new ResponseEntity<>("Successful", HttpStatus.OK);
    }

    @PutMapping("/topic/assign")
    @PreAuthorize("hasRole('ROLE_CAN_MANAGE_COACH')")
    public ResponseEntity<?> assignTopic(@RequestParam String topic,
                                         @RequestParam Long userId) {
        coachService.assignTopicToCoach(topic, userId);
        return new ResponseEntity<>("Successful", HttpStatus.OK);
    }

    @PutMapping("/topic/deAssign")
    @PreAuthorize("hasRole('ROLE_CAN_MANAGE_COACH')")
    public ResponseEntity<?> deAssignTopic(@RequestParam String topic,
                                         @RequestParam Long userId) {
        coachService.deAssignTopicFromCoach(topic, userId);
        return new ResponseEntity<>("Successful", HttpStatus.OK);
    }

    @PutMapping("/deactivate")
    @PreAuthorize("hasRole('ROLE_CAN_MANAGE_COACH')")
    public ResponseEntity<?> deactivateCoach(@RequestParam(required = false) Long userId) {
        coachService.deactivateCoach(userId);
        return new ResponseEntity<>("Successful", HttpStatus.OK);
    }

    @GetMapping("/list/active")
    public ResponseEntity<?> listActiveCoaches(@RequestParam(defaultValue = "firstname", required = false) String orderBy,
                                               @RequestParam(defaultValue = "asc", required = false) String orderDirection,
                                                @RequestParam(defaultValue = "0", required = false) Integer page,
                                               @RequestParam(defaultValue = "10", required = false) Integer size) {
        return new ResponseEntity<>(coachService.listActiveCoaches(orderDirection, orderBy, page, size),
                HttpStatus.OK);
    }

    @GetMapping("/list/all")
    public ResponseEntity<?> listAllCoaches(@RequestParam(defaultValue = "firstname", required = false) String orderBy,
                                               @RequestParam(defaultValue = "asc", required = false) String orderDirection,
                                                @RequestParam(defaultValue = "0", required = false) Integer page,
                                               @RequestParam(defaultValue = "10", required = false) Integer size) {
        return new ResponseEntity<>(coachService.listAllCoaches(orderDirection, orderBy, page, size),
                HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchCoaches(@RequestParam String name,
                                           @RequestParam(defaultValue = "0", required = false) Integer page,
                                           @RequestParam(defaultValue = "10", required = false) Integer size) {
        return new ResponseEntity<>(coachService.searchCoachByName(name, page, size), HttpStatus.OK);
    }
    @GetMapping("/topic")
    public ResponseEntity<?> getCoachesByTopic(@RequestParam List<String> topics,
                                           @RequestParam(defaultValue = "0", required = false) Integer page,
                                           @RequestParam(defaultValue = "10", required = false) Integer size) {
        return new ResponseEntity<>(coachService.getActiveCoachesByTopics(topics, page, size), HttpStatus.OK);
    }
}
