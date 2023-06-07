package com.chibuisi.dailyinsightservice.springsecapp.service;

import com.chibuisi.dailyinsightservice.mail.model.EmailTemplate;
import com.chibuisi.dailyinsightservice.mail.service.serviceimpl.JavaMailService;
import com.chibuisi.dailyinsightservice.springsecapp.model.ResetPasswordDTO;
import com.chibuisi.dailyinsightservice.springsecapp.model.UserAccount;
import com.chibuisi.dailyinsightservice.springsecapp.util.ResetPasswordTokenGenerator;
import com.chibuisi.dailyinsightservice.template.TemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ResetPasswordService {
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private JavaMailService javaMailService;
    @Autowired
    private ResetPasswordTokenGenerator tokenGenerator;
    @Autowired
    private TemplateUtil templateUtil;

    private final String RESET_PASSWORD_TEMPLATE = "resetpassword";
    private final String LINK = "http://localhost:10240/reset-Password";

    public void sendPasswordResetEmail(ResetPasswordDTO resetPasswordDTO) {
        final Optional<UserAccount> optionalUserAccountDTO =
                userDetailsService.getUserAccountFromEmailOrUsername(resetPasswordDTO.getEmailOrUsername());
        if (optionalUserAccountDTO.isPresent()) {
            UserAccount userAccount = optionalUserAccountDTO.get();

            final String token = tokenGenerator.generateToken();
            final String resetPasswordLink = LINK+"?email="+userAccount.getEmail()+"&token="+token;
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime expirationDateTime = now.plus(24, ChronoUnit.HOURS);

            userAccount.setResetToken(token);
            userAccount.setResetTokenValidity(expirationDateTime);
            userDetailsService.saveUserAccount(userAccount);

            Map<String, Object> additionalData = new HashMap<>();
            additionalData.put("resetPasswordLink", resetPasswordLink);
            additionalData.put("email", userAccount.getEmail());

            String htmlTemplate = templateUtil.getTemplate(RESET_PASSWORD_TEMPLATE, additionalData);
            if(htmlTemplate == null) {
                System.out.println("Email Template was not found");
                return;
            }
            EmailTemplate emailTemplate = EmailTemplate.builder()
                    .email(userAccount.getEmail())
                    .subject("Reset Your password")
                    .template(htmlTemplate)
                    .additionalData(additionalData).build();

            javaMailService.sendEmail(emailTemplate);
        }

    }
}
