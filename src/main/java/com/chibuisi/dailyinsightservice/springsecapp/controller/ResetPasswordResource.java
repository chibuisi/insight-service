package com.chibuisi.dailyinsightservice.springsecapp.controller;

import com.chibuisi.dailyinsightservice.springsecapp.model.ResetPasswordDTO;
import com.chibuisi.dailyinsightservice.springsecapp.service.ResetPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResetPasswordResource {
    @Autowired
    private ResetPasswordService resetPasswordService;

    @PostMapping("/reset/sendEmail")
    public ResponseEntity<?> sendPasswordReset(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        resetPasswordService.sendPasswordResetEmail(resetPasswordDTO);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @PostMapping("/reset/updatePassword")
    public ResponseEntity<?> updatePassword() {
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
