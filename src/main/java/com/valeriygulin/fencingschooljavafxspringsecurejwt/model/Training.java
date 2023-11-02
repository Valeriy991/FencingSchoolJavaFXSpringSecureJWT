package com.valeriygulin.fencingschooljavafxspringsecurejwt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Training {

    private long id;
    private long numberGym;
    private LocalDate date;
    private LocalTime timeStart;
    private Apprentice apprentice;
    private Trainer trainer;

    public Training(Apprentice apprentice, Trainer trainer, long numberGym, LocalDate date, LocalTime timeStart) {
        this.apprentice = apprentice;
        this.trainer = trainer;
        this.numberGym = numberGym;
        this.date = date;
        this.timeStart = timeStart;
    }

    @Override
    public String toString() {
        return "id: " + id +
                ", номер зала: " + numberGym +
                ", дата=" + date +
                ", время начала: =" + timeStart +
                ", ученик: " + apprentice.getUserName() + " " + apprentice.getFirstName() +
                ", тренер: " + trainer.getUserName() + " " + trainer.getFirstName();

    }
}
