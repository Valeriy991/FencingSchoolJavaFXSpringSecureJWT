package com.valeriygulin.fencingschooljavafxspringsecurejwt.model;

import lombok.*;


@Data
@EqualsAndHashCode(callSuper = true)
public class Admin extends Account {
    public Admin(@NonNull String firstName,
                 @NonNull String userName, @NonNull String lastName,
                 @NonNull String login, @NonNull String password,
                 @NonNull Role role) {
        super(firstName, userName, lastName, login, password, role);
    }

    @Override
    public String toString() {
        return "id: " + this.getId() + " ФИО: " +
                this.getFirstName() + " " + this.getUserName()
                + " " + this.getLastName();
    }
}
