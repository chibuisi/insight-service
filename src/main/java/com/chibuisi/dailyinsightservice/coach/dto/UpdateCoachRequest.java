package com.chibuisi.dailyinsightservice.coach.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateCoachRequest {
    @NotNull(message = "user id is required.")
    @Digits(integer = 10, fraction = 0, message = "user id must have a valid value")
    private String userId;
    @NotBlank(message = "firstname is required")
    @Size(max = 255, message = "firstname must be maximum {max} characters")
    private String firstname;
    @NotBlank(message = "lastname is required")
    @Size(max = 255, message = "lastname must be maximum {max} characters")
    private String lastname;
    @Size(max = 10, message = "specialization must not exceed {max}")
    private List<@Size(min = 2, max = 50, message = "specialization(s) must be between {min} and {max} characters") String> specialization;
    @NotNull(message = "bio must not be null")
    @Size(max = 5000, message = "bio must be maximum {max} characters")
    private String bio;
    @NotBlank(message = "phone is required")
    @Size(min = 5, max = 30, message = "phone must be between {min} and {max} characters")
    @Pattern(regexp = "^\\+?[\\d\\s.()-]+$", message = "phone must match the pattern ^\\+?[\\d\\s.()-]+$")
    private String phone;
    @NotNull(message = "availability must not be null")
    private List<@Size(min = 5, max = 50, message = "availability must be between {min} and {max} characters") String> availability;
    @NotNull(message = "image links must not be null")
    @Size(max = 10, message = "image link(s) must not exceed {max}")
    private List<@Size(min = 5, max = 100, message = "image link(s) must be between {min} and {max} characters") String> imageLinks;
    @NotNull(message = "languages must not be null")
    @Size(min = 1, max = 10, message = "a minimum of {min} and maximum of {max} language(s) is required ")
    private List<@Size(min = 2, max = 50, message = "language(s) must be between {min} and {max} characters") String> languages;
    @NotNull(message = "certifications must not be null")
    @Size(max = 10, message = "certifications must not exceed {max}")
    private List<@Size(min = 2, max = 100, message = "certification(s) must be between {min} and {max} characters") String> certifications;
    @NotNull(message = "experience must not be null")
    @Size(max = 5000, message = "experience must be maximum {max} characters")
    private String experience;
}
