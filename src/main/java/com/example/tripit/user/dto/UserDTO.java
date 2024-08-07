package com.example.tripit.user.dto;

import jakarta.persistence.Column;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Builder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private long userId;

    private String email;
    private String username;
    private String nickname;
    private String password;
    private String birth;
    private String gender;

    private String userIntro;

    private String role;

    private LocalDate regdate;

    private String social_type;
    private String userpic;

    private int reportCount;
    private LocalDate endDate;

}
