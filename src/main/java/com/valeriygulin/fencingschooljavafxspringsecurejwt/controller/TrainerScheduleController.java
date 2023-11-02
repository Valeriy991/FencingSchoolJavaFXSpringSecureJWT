package com.valeriygulin.fencingschooljavafxspringsecurejwt.controller;

import com.valeriygulin.fencingschooljavafxspringsecurejwt.App;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.model.Trainer;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.retrofit.TrainerScheduleRepository;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseButton;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.prefs.Preferences;

public class TrainerScheduleController implements ControllerData<Trainer> {
    @FXML
    public ComboBox comboBoxDayWeek;
    @FXML
    public ComboBox comboBoxStartTime;
    @FXML
    public ComboBox comboBoxEndTime;
    private Trainer trainer;
    private Preferences prefs = Preferences.userRoot().node(App.class.getName());


    @FXML
    public void buttonAdd(ActionEvent actionEvent) throws IOException {
        String dayWeek = (String) this.comboBoxDayWeek.getSelectionModel().getSelectedItem();
        if (dayWeek == null) {
            App.showAlert("Error!", "Выберите день недели!", Alert.AlertType.ERROR);
            return;
        }
        LocalTime startTime = (LocalTime) this.comboBoxStartTime.getSelectionModel().getSelectedItem();
        if (startTime == null) {
            App.showAlert("Error!", "Выберете начальное время!", Alert.AlertType.ERROR);
            return;
        }
        LocalTime endTime = (LocalTime) this.comboBoxEndTime.getSelectionModel().getSelectedItem();
        if (endTime == null) {
            App.showAlert("Error!", "Выберите время окончания!", Alert.AlertType.ERROR);
            return;
        }
        if (startTime.getHour() >= endTime.getHour() && startTime.getMinute() > endTime.getMinute()) {
            App.showAlert("Error!", "Время окончания должно быть больше начального времени!", Alert.AlertType.ERROR);
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("Понедельник", "monday");
        map.put("Вторник", "tuesday");
        map.put("Среда", "wednesday");
        map.put("Четверг", "thursday");
        map.put("Пятница", "friday");
        map.put("Суббота", "saturday");
        map.put("Воскресенье", "sunday");
        String dayWeekConvert = map.getOrDefault(dayWeek, "");
        try {
            new TrainerScheduleRepository().post(this.trainer.getId(), dayWeekConvert,
                    startTime, endTime, this.prefs.get("token", ""));
            App.showAlert("Info", "Новое расписание для тренера " + this.trainer.getUserName() +
                    " добавлено!", Alert.AlertType.INFORMATION);
            this.initData(this.trainer);
            App.closeWindow(actionEvent);
        } catch (NullPointerException e) {
            App.showAlert("Error", "Нет права доступа! ", Alert.AlertType.ERROR);
        } catch (IllegalArgumentException e) {
            App.showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }

    }

    @Override
    public void initData(Trainer trainer) throws IOException {
        this.trainer = trainer;
        List<String> dayWeek = new ArrayList<>();
        dayWeek.add("Понедельник");
        dayWeek.add("Вторник");
        dayWeek.add("Среда");
        dayWeek.add("Четверг");
        dayWeek.add("Пятница");
        dayWeek.add("Суббота");
        dayWeek.add("Воскресенье");
        this.comboBoxDayWeek.setItems(FXCollections.observableList(dayWeek));
        List<LocalTime> times = new ArrayList<>();
        LocalTime time1 = LocalTime.parse("07:00");
        times.add(time1);
        for (int i = 0; i < 32; i++) {
            time1 = time1.plusMinutes(30);
            times.add(time1);
        }
        this.comboBoxStartTime.setItems(FXCollections.observableList(times));
        this.comboBoxStartTime.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                if (mouseEvent.getClickCount() == 1) {
                    String dayWeek1 = (String) comboBoxDayWeek.getSelectionModel().getSelectedItem();
                    if (dayWeek1 == null) {
                        App.showAlert("Info", "Выберите день недели!", Alert.AlertType.INFORMATION);
                    }
                }
            }
        });

        this.comboBoxEndTime.setItems(FXCollections.observableList(times));
        this.comboBoxEndTime.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                if (mouseEvent.getClickCount() == 1) {
                    LocalTime startTime = (LocalTime) comboBoxStartTime.getSelectionModel().getSelectedItem();
                    if (startTime == null) {
                        App.showAlert("Info", "Выберите время начала!", Alert.AlertType.INFORMATION);
                    }
                }
            }
        });
    }
}
