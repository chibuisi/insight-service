package com.chibuisi.dailyinsightservice.user.model;

import com.chibuisi.dailyinsightservice.topic.model.UserTopicItemOffset;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    //@Column(unique = true)
    private String email;
    private LocalDateTime dateJoined;
    private String ipAddress;
    private String timezone;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "userId")
    private List<UserTopicItemOffset> userTopicItemOffsets;
}
