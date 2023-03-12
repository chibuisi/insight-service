package com.chibuisi.dailyinsightservice.user.service.impl;

import com.chibuisi.dailyinsightservice.user.model.User;
import com.chibuisi.dailyinsightservice.user.repository.UserRepository;
import com.chibuisi.dailyinsightservice.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        user.setDateJoined(LocalDateTime.now());
        User existingUser = userRepository.getUserByEmail(user.getEmail());
        if(existingUser != null)
            return existingUser;
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        User existingUser = userRepository.getUserByEmail(user.getEmail());
        if(existingUser == null)
            return user;
        existingUser.setIpAddress(user.getIpAddress());
        existingUser.setFirstname(user.getFirstname());
        existingUser.setLastname(user.getLastname());
        existingUser.setTimezone(user.getTimezone());
        return userRepository.save(existingUser);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> getUsersById(List<Long> ids) {
        return userRepository.findAllById(ids);
    }
}
