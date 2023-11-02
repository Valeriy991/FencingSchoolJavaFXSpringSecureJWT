package com.valeriygulin.fencingschooljavafxspringsecurejwt.model;

import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Trainer extends Account {

    @NonNull
    private int experience;


    private TrainerSchedule trainerSchedule;

    private List<Training> trainings;

    public Trainer(@NonNull String login, @NonNull String password,
                   @NonNull String firstName, @NonNull String userName,
                   @NonNull String lastName, @NonNull Role role, int experience) {
        super(login, password, firstName, userName, lastName, role);
        this.experience = experience;
    }


    public Trainer(long id, Date created, Date updated, @NonNull String login,
                   @NonNull String password, @NonNull String firstName, @NonNull String userName,
                   @NonNull String lastName, @NonNull Role role, Status status, @NonNull int experience) {
        super(id, created, updated, login, password, firstName, userName, lastName, role, status);
        this.experience = experience;
    }


    @Override
    public String toString() {
        return "id: " + this.getId() + " ФИО: " + this.getFirstName() + " " +
                this.getUserName() + " " + this.getLastName() +
                " Опыт: " + this.getExperience();
    }
}
