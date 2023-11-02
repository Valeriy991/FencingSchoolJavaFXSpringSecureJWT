package com.valeriygulin.fencingschooljavafxspringsecurejwt.model;

import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)

public class Apprentice extends Account {
    @NonNull
    private String phoneNumber;

    public Apprentice(@NonNull String login, @NonNull String password, @NonNull String firstName,
                      @NonNull String userName, @NonNull String lastName, @NonNull String phoneNumber,@NonNull Role role) {
        super(login, password, firstName, userName, lastName,role);
        this.phoneNumber = phoneNumber;
    }

    public Apprentice(long id, Date created, Date updated, @NonNull String login,
                      @NonNull String password, @NonNull String firstName, @NonNull String userName,
                      @NonNull String lastName, @NonNull Role role, Status status, @NonNull String phoneNumber) {
        super(id, created, updated, login, password, firstName, userName, lastName, role, status);
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "id:" + this.getId() + " ФИО: " + this.getFirstName() +
                " " + this.getUserName() + " " + this.getLastName() +
                " Телефон: " + this.getPhoneNumber();
    }


}