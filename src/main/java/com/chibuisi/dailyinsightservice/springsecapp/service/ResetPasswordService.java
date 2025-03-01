package com.chibuisi.dailyinsightservice.springsecapp.service;

import com.chibuisi.dailyinsightservice.mail.model.EmailTemplate;
import com.chibuisi.dailyinsightservice.mail.service.serviceimpl.JavaMailService;
import com.chibuisi.dailyinsightservice.springsecapp.EmailTemplateIdentifier;
import com.chibuisi.dailyinsightservice.springsecapp.model.ResetPasswordDTO;
import com.chibuisi.dailyinsightservice.springsecapp.model.UpdatePasswordDTO;
import com.chibuisi.dailyinsightservice.springsecapp.model.UserAccount;
import com.chibuisi.dailyinsightservice.springsecapp.util.ResetPasswordTokenGenerator;
import com.chibuisi.dailyinsightservice.template.TemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TemplateUtil templateUtil;

    @Value("${application.ui.service-url}")
    private String APPLICATION_UI_SERVICE_URL;

    private final String RESET_PASSWORD_TEMPLATE = "resetpassword";

    public void sendPasswordResetEmail(ResetPasswordDTO resetPasswordDTO) {
        final String LINK = APPLICATION_UI_SERVICE_URL+"/update-password";
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

    public boolean updatePassword(UpdatePasswordDTO updatePasswordDTO) {
        final Optional<UserAccount> optionalUserAccountDTO =
                userDetailsService.getUserAccountFromEmailOrUsername(updatePasswordDTO.getEmailOrUsername());
        if(!optionalUserAccountDTO.isPresent())
            return false;
        UserAccount userAccount = optionalUserAccountDTO.get();

        if(!userAccount.getResetToken().equals(updatePasswordDTO.getToken()))
            return false;

        LocalDateTime now = LocalDateTime.now();
        if (userAccount.getResetTokenValidity().isBefore(now)) {
            return false;
        }

        userAccount.setPassword(passwordEncoder.encode(updatePasswordDTO.getPassword()));
        userAccount.setResetTokenValidity(now);
        userDetailsService.saveUserAccount(userAccount);

        sendPasswordResetConfirmationEmail(userAccount.getEmail());

        return true;
    }

    private void sendPasswordResetConfirmationEmail(String email) {
        Map<String, Object> additionalData = new HashMap<>();
        additionalData.put("email", email);

        EmailTemplateIdentifier templateIdentifier = EmailTemplateIdentifier.RESET_PASSWORD_CONFIRMATION;
        String htmlTemplate = templateUtil.getTemplate(templateIdentifier, additionalData);
        if(htmlTemplate == null) {
            System.out.println("Email Template was not found");
            return;
        }
        EmailTemplate emailTemplate = EmailTemplate.builder()
                .email(email)
                .subject("Password Reset Confirmation")
                .template(htmlTemplate)
                .additionalData(additionalData).build();

        javaMailService.sendEmail(emailTemplate);
    }
}
