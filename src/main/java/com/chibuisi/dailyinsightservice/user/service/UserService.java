package com.chibuisi.dailyinsightservice.user.service;

import com.chibuisi.dailyinsightservice.user.model.User;

import java.util.List;

public interface UserService {
    public User saveUser(User user);
    public User updateUser(User user);
    public User getUserByEmail(String email);
    public User getUserById(Long id);
    public List<User> getUsersById(List<Long> ids);
}
