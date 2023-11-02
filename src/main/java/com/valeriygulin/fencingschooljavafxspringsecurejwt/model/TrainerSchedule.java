package com.valeriygulin.fencingschooljavafxspringsecurejwt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class TrainerSchedule {
    private long idTrainer;
    private LocalTime mondayStart;
    private LocalTime mondayEnd;
    private LocalTime tuesdayEnd;
    private LocalTime tuesdayStart;
    private LocalTime wednesdayStart;
    private LocalTime wednesdayEnd;
    private LocalTime thursdayStart;
    private LocalTime thursdayEnd;
    private LocalTime fridayStart;
    private LocalTime fridayEnd;
    private LocalTime saturdayStart;
    private LocalTime saturdayEnd;
    private LocalTime sundayStart;
    private LocalTime sundayEnd;

    public List<TrainerScheduleItem> convert(long idTrainer) {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("monday", "Понедельник");
            map.put("tuesday", "Вторник");
            map.put("wednesday", "Среда");
            map.put("thursday", "Четверг");
            map.put("friday", "Пятница");
            map.put("saturday", "Суббота");
            map.put("sunday", "Воскресенье");
            String[] dayWeek = new String[]{"monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"};
            List<TrainerScheduleItem> res = new ArrayList<>();
            for (int i = 0; i < dayWeek.length; i++) {
                Field declaredField = this.getClass().getDeclaredField(dayWeek[i] + "Start");
                Field declaredField2 = this.getClass().getDeclaredField(dayWeek[i] + "End");
                LocalTime timeStart = (LocalTime) declaredField.get(this);
                LocalTime timeEnd = (LocalTime) declaredField2.get(this);
                if (timeStart != null && timeEnd != null) {
                    TrainerScheduleItem trainerScheduleItem =
                            new TrainerScheduleItem(map.getOrDefault(dayWeek[i], ""),
                                    timeStart, timeEnd);
                    res.add(trainerScheduleItem);
                }
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<TrainerScheduleItem> convertDayWeek() {
        try {
            String[] dayWeek = new String[]{"monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"};
            List<TrainerScheduleItem> res = new ArrayList<>();
            for (int i = 0; i < dayWeek.length; i++) {
                Field declaredField = this.getClass().getDeclaredField(dayWeek[i] + "Start");
                Field declaredField2 = this.getClass().getDeclaredField(dayWeek[i] + "End");
                LocalTime timeStart = (LocalTime) declaredField.get(this);
                LocalTime timeEnd = (LocalTime) declaredField2.get(this);
                if (timeStart != null && timeEnd != null) {
                    TrainerScheduleItem trainerScheduleItem =
                            new TrainerScheduleItem(dayWeek[i], timeStart, timeEnd);
                    res.add(trainerScheduleItem);
                }
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
