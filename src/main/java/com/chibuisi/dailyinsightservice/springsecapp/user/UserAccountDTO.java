package com.chibuisi.dailyinsightservice.springsecapp.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAccountDTO {
    private String email;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String ipAddress;
    private String timezone;
    private Boolean agreedToEula;

}
