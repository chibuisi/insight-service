package com.chibuisi.dailyinsightservice.coach.repository;

import com.chibuisi.dailyinsightservice.coach.model.Coach;
import com.chibuisi.dailyinsightservice.topic.model.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoachRepository extends JpaRepository<Coach, Long> {
    Optional<Coach> findByIdAndActiveIs(Long id, Boolean active);
    Page<Coach> findAllByActiveIsAndTopicsIn(Boolean active, List<Topic> topics, Pageable pageable);
    List<Coach> findAllByIdIn(List<Long> userIds);
    Optional<Coach> findByUserId(Long userId);
    Optional<Coach> findByUserIdAndActiveIs(Long userId, Boolean active);
    Page<Coach> findAllByFirstnameAndLastnameAndActiveIs(String firstname, String lastname, Boolean active, Pageable pageable);
    Page<Coach> findAllBySpecializationAndActiveIs(String specialization, Boolean active, Pageable pageable);
    Page<Coach> findAllByCertificationsLikeAndActiveIs(String specialization, Boolean active, Pageable pageable);
    Page<Coach> findAllByFeaturedIsAndActiveIs(Boolean featured, Boolean active, Pageable pageable);
}
