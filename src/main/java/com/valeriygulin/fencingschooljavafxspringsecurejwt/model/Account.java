package com.valeriygulin.fencingschooljavafxspringsecurejwt.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Account {
    private long id;
    @CreatedDate
    private Date created;
    @LastModifiedDate
    private Date updated;
    @NonNull
    private String login;
    @NonNull
    private String password;
    @NonNull
    private String firstName;
    @NonNull
    private String userName;
    @NonNull
    private String lastName;
    @NonNull
    private Role role;
    private Status status;
}
