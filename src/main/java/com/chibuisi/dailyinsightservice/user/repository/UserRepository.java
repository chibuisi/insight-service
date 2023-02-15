package com.chibuisi.dailyinsightservice.user.repository;

import com.chibuisi.dailyinsightservice.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User getUserByEmail(String email);
    public User getUserByIpAddress(String ipAddress);
}
