package com.chibuisi.dailyinsightservice.springsecapp.user;

import com.chibuisi.dailyinsightservice.springsecapp.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserAccountController {
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @PostMapping("/signup")
    public ResponseEntity<?> saveUserAccount(@RequestBody UserAccountDTO userAccountDTO){
        return new ResponseEntity<>(myUserDetailsService.saveUserAccount(userAccountDTO), HttpStatus.CREATED);
    }

    @GetMapping("/check-username")
    public ResponseEntity<?> checkUsernameAvailability(@RequestParam String username){
        boolean exist = myUserDetailsService.checkUsernameAvailability(username);
        if(exist)
            return new ResponseEntity<>(false, HttpStatus.OK);
        else
            return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getUser(@RequestParam String user){
        Optional<UserAccountDTO> userAccountDTO = myUserDetailsService.findUserByCredentials(user);
        if(!userAccountDTO.isPresent())
            return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(userAccountDTO, HttpStatus.OK);
    }

    @GetMapping("/role")
    public void getUserRoles(){
        
    }

    @PostMapping("/role")
    public void addRole(){

    }
}
