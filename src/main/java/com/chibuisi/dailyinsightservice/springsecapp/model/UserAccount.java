package com.chibuisi.dailyinsightservice.springsecapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_account")
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 216)
    private String email;

    @Column(nullable = false, unique = true, length = 216)
    private String username;

    @Column(nullable = false, length = 216)
    private String password;

    @Column(name = "firstname", length = 216)
    private String firstName;

    @Column(name = "lastname", length = 216)
    private String lastName;

    private LocalDateTime dateJoined;
    @Column(name = "ipAddress", length = 216)
    private String ipAddress;
    private String timezone;
    private Boolean agreedToEula;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_account_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<AppRole> roles = new ArrayList<>();

    @Column(name = "reset_token", length = 216)
    private String resetToken;

    @Column(name = "reset_token_validity")
    private LocalDateTime resetTokenValidity;
}