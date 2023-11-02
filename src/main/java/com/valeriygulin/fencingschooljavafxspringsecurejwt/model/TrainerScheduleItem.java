package com.valeriygulin.fencingschooljavafxspringsecurejwt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainerScheduleItem {
    private String dayWeek;
    private LocalTime timeStart;
    private LocalTime timeEnd;
}
