package com.chibuisi.dailyinsightservice.coach.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCoachRequest {
    @NotBlank(message = "user id is required")
    private Long userId;
    @NotBlank(message = "email is required")
    @Size(max = 500, message = "lastname is required")
    @Email(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
    message = "email format is invalid")
    private String email;
    @NotBlank(message = "firstname is required")
    @Size(max = 255, message = "firstname is required")
    @Pattern(regexp = "^[a-zA-Z\\s']+$", message = "Invalid character(s) in firstname.")
    private String firstname;
    @NotBlank(message = "lastname is required")
    @Size(max = 255, message = "lastname is required")
    @Pattern(regexp = "^[a-zA-Z\\s']+$", message = "Invalid character(s) in lastname.")
    private String lastname;
    @NotBlank(message = "specialization is required")
    @Size(max = 1000, message = "specialization is required")
    private String specialization;
    @NotEmpty(message = "bio must not be null")
    @Size(max = 5000, message = "specialization is required")
    private String bio;
    @NotBlank(message = "phone is required")
    @Size(min = 5, max = 20, message = "phone must be between {min} and {max} characters")
    private String phone;
    @NotEmpty(message = "availability must not be null")
    @Size(max = 500, message = "availability must be a maximum of {max} characters")
    private String availability;
    @Valid
    @Size(max = 255, message = "each specialization must be a maximum of {max} characters")
    private List<String> imageLinks;
    @Valid
    @Size(max = 255, message = "each language must be a maximum of {max} characters")
    private List<String> languages;
    @Valid
    @Size(max = 255, message = "each certification must be a maximum of {max} characters")
    private List<String> certifications;
    @NotEmpty(message = "experience must not be null")
    @Size(max = 5000, message = "experience is required")
    private String experience;
    @Valid
    @Size(max = 255, message = "each topic must be a maximum of {max} characters")
    private List<String> topics;
}
