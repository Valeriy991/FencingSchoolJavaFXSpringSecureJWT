package com.valeriygulin.fencingschooljavafxspringsecurejwt.controller;

import com.valeriygulin.fencingschooljavafxspringsecurejwt.App;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.model.Apprentice;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.model.Trainer;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.model.TrainerSchedule;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.model.TrainerScheduleItem;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.retrofit.TrainerRepository;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.retrofit.TrainerScheduleRepository;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.retrofit.TrainingRepository;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

public class TrainingAddFormController implements ControllerData<Apprentice> {

    @FXML
    public ComboBox comboBoxTrainers;
    @FXML
    public DatePicker datePickerOfNewTrainingEditor;
    @FXML
    public TextField textFieldNumGym;
    @FXML
    public ComboBox comboBoxOfTimeNewTraining;
    private Trainer trainer;
    private Apprentice apprentice;
    private Preferences prefs = Preferences.userRoot().node(App.class.getName());

    @Override
    public void initData(Apprentice value) throws IOException {
        this.apprentice = value;
    }

    @FXML
    public void buttonAddNewTraining(ActionEvent actionEvent) throws IOException {
        Trainer trainer = (Trainer) this.comboBoxTrainers.getSelectionModel().getSelectedItem();
        if (trainer == null) {
            App.showAlert("Error!", "Выберите тренировку!", Alert.AlertType.ERROR);
            return;
        }
        LocalDate date = this.datePickerOfNewTrainingEditor.getValue();
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String text = date.format(formatters);
        if (text == null) {
            App.showAlert("Error!", "Выберите дату!", Alert.AlertType.ERROR);
            return;
        }
        String numGym = this.textFieldNumGym.getText();
        if (numGym == null) {
            App.showAlert("Error!", "Выберите номер зала!", Alert.AlertType.ERROR);
            return;
        }
        LocalTime timeStart = (LocalTime) this.comboBoxOfTimeNewTraining.getSelectionModel().getSelectedItem();
        if (timeStart == null) {
            App.showAlert("Error!", "Выберите время начала тренировки!", Alert.AlertType.ERROR);
            return;
        }
        try {
            if (this.apprentice != null) {
                new TrainingRepository().post(this.apprentice.getId(), trainer.getId(),
                        Long.parseLong(numGym), text, timeStart, this.prefs.get("token", ""));
            } else {
                long id = Long.parseLong(prefs.get("id", ""));
                new TrainingRepository().post(id, trainer.getId(),
                        Long.parseLong(numGym), text, timeStart, this.prefs.get("token", ""));
            }

        } catch (IllegalArgumentException e) {
            App.showAlert("Info", e.getMessage(), Alert.AlertType.INFORMATION);
            return;
        }
        App.showAlert("Info", "Тренировка была добавлена!", Alert.AlertType.INFORMATION);
        App.closeWindow(actionEvent);
    }

    @FXML
    public void initialize() throws IOException {
        List<Trainer> all = new TrainerRepository().getAll(this.prefs.get("token", ""));
        this.comboBoxTrainers.setItems(FXCollections.observableList(all));
        this.datePickerOfNewTrainingEditor.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                if (mouseEvent.getClickCount() == 1) {
                    Trainer trainer = (Trainer) comboBoxTrainers.getSelectionModel().getSelectedItem();
                    if (trainer == null) {
                        App.showAlert("Info", "Выберите тренера!", Alert.AlertType.INFORMATION);
                    }
                }
            }
        });
        this.textFieldNumGym.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                if (mouseEvent.getClickCount() == 1) {
                    LocalDate date = datePickerOfNewTrainingEditor.getValue();
                    if (date == null) {
                        App.showAlert("Info", "Выберите дату!", Alert.AlertType.INFORMATION);
                    }
                }
            }
        });
        this.comboBoxOfTimeNewTraining.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                if (mouseEvent.getClickCount() == 1) {
                    String numGym = textFieldNumGym.getText();
                    if (numGym.isEmpty()) {
                        App.showAlert("Info", "Выберите номер зала!", Alert.AlertType.INFORMATION);
                    } else {
                        if (!textFieldNumGym.getText().isEmpty()) {
                            LocalTime[] localTimes = new LocalTime[0];
                            try {
                                LocalDate value = datePickerOfNewTrainingEditor.getValue();
                                datePickerOfNewTrainingEditor.getValue();
                                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                                String format = dateTimeFormatter.format(value);
                                localTimes = new TrainerScheduleRepository().get(trainer.getId(),
                                        format, this.prefs.get("token", ""));
                            } catch (IllegalArgumentException | IOException e) {
                                App.showAlert("Info", e.getMessage(), Alert.AlertType.INFORMATION);
                            }
                            List<LocalTime> res = new ArrayList<>();
                            for (int i = 0; i < localTimes.length; i++) {
                                res.add(localTimes[i]);
                            }
                            comboBoxOfTimeNewTraining.setItems(FXCollections.observableList(res));
                        } else {
                            App.showAlert("Info", "Выберите номер зала!", Alert.AlertType.INFORMATION);
                        }
                    }
                }
            }
        });
        this.datePickerOfNewTrainingEditor.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                Trainer trainer1 = (Trainer) comboBoxTrainers.getSelectionModel().getSelectedItem();
                trainer = trainer1;
                try {
                    if (date.isBefore(LocalDate.now())) {
                        setDisable(true);
                        setStyle("-fx-background-color: #ff1943;");
                    } else {
                        TrainerSchedule trainerSchedule = new TrainerScheduleRepository().get(trainer1.getId(), prefs.get("token", ""));
                        List<TrainerScheduleItem> trainerScheduleItems = trainerSchedule.convertDayWeek();
                        String dayWeek = date.getDayOfWeek().toString().toLowerCase();
                        int count = 0;
                        for (TrainerScheduleItem trainerScheduleItem : trainerScheduleItems) {
                            if (dayWeek.equals(trainerScheduleItem.getDayWeek())) {
                                setStyle("-fx-background-color: #0af759;");
                                count++;
                            }
                        }
                        if (count != 1) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ff1943;");
                        }
                    }
                } catch (IllegalArgumentException | IOException e) {
                }
            }
        });

    }


}
