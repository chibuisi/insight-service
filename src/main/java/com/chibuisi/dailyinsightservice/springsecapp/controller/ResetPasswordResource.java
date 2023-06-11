package com.chibuisi.dailyinsightservice.springsecapp.controller;

import com.chibuisi.dailyinsightservice.springsecapp.model.ResetPasswordDTO;
import com.chibuisi.dailyinsightservice.springsecapp.model.UpdatePasswordDTO;
import com.chibuisi.dailyinsightservice.springsecapp.service.ResetPasswordService;
import com.google.common.base.Preconditions;
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
        Preconditions.checkNotNull(resetPasswordDTO, "Request object must not be null");
        Preconditions.checkNotNull(resetPasswordDTO.getEmailOrUsername(), "EmailOrUsername must not be null");
        resetPasswordService.sendPasswordResetEmail(resetPasswordDTO);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @PostMapping("/reset/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordDTO updatePasswordDTO) {
        Preconditions.checkNotNull(updatePasswordDTO, "Request object must not be null");
        Preconditions.checkNotNull(updatePasswordDTO.getEmailOrUsername(), "EmailOrUsername must not be null");
        Preconditions.checkNotNull(updatePasswordDTO.getPassword(), "Password must not be null");
        Preconditions.checkNotNull(updatePasswordDTO.getToken(), "Token must not be null");

        boolean result = resetPasswordService.updatePassword(updatePasswordDTO);
        if(result)
            return new ResponseEntity<>("Success", HttpStatus.OK);
        return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
    }
}
