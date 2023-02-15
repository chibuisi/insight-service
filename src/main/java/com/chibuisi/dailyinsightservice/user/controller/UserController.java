package com.chibuisi.dailyinsightservice.user.controller;

import com.chibuisi.dailyinsightservice.user.model.User;
import com.chibuisi.dailyinsightservice.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public User saveUser(@RequestBody User user, HttpServletRequest request){
        user.setIpAddress(request.getRemoteAddr());
        return userService.saveUser(user);
    }

    @PutMapping
    public User updateUser(User user){
        return userService.updateUser(user);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @GetMapping
    public User getUserByEmail(@RequestParam(required = true) String email){
        return userService.getUserByEmail(email);
    }

    @GetMapping("/users")
    public List<User> getUsersById(@RequestParam(required = true) List<Long> ids){
        return userService.getUsersById(ids);
    }
}
