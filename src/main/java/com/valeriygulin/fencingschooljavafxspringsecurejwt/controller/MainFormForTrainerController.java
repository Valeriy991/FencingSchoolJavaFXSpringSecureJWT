package com.valeriygulin.fencingschooljavafxspringsecurejwt.controller;

import com.valeriygulin.fencingschooljavafxspringsecurejwt.App;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.model.Trainer;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.model.TrainerSchedule;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.model.TrainerScheduleItem;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.retrofit.TrainerScheduleRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.prefs.Preferences;

public class MainFormForTrainerController {
    @FXML
    public Label labelWelcome;
    @FXML
    public TableView tableViewTrainings;
    @FXML
    public TableColumn tableColumnDayWeek;
    @FXML
    public TableColumn tableColumnStartTime;
    @FXML
    public TableColumn tableColumnEndTime;
    private Preferences prefs = Preferences.userRoot().node(App.class.getName());

    private long id = Long.parseLong(prefs.get("id", ""));

    @FXML
    public void buttonLogOut(ActionEvent actionEvent) throws IOException {
        this.prefs.put("token", "");
        this.prefs.put("login", "");
        this.prefs.put("password", "");
        App.openWindow("authorizationForm.fxml", "Форма авторизации", null);
        App.closeWindow(actionEvent);
    }

    @FXML
    public void buttonAddApprentice(ActionEvent actionEvent) throws IOException {
        App.openWindowAndWait("addFormApprentice.fxml", "Форма добавления ученика", null);
        this.initialize();
    }

    @FXML
    public void initialize() throws IOException {
        try {
            this.tableViewTrainings.getItems().clear();
            /*this.tableColumnDayWeek.getColumns().clear();
            this.tableColumnStartTime.getColumns().clear();
            this.tableColumnEndTime.getColumns().clear();*/
            String login = this.prefs.get("login", "");
            String text = this.labelWelcome.getText();
            if (!text.contains(login)) {
                String res = text + login;
                this.labelWelcome.setText(res);
            }
            this.tableColumnDayWeek.setCellValueFactory(new PropertyValueFactory<TrainerScheduleItem, String>("dayWeek"));
            this.tableColumnStartTime.setCellValueFactory(new PropertyValueFactory<TrainerScheduleItem, String>("timeStart"));
            this.tableColumnEndTime.setCellValueFactory(new PropertyValueFactory<TrainerScheduleItem, String>("timeEnd"));
            TrainerSchedule trainerSchedule = new TrainerScheduleRepository().get(this.id,
                    prefs.get("token", null));
            List<TrainerScheduleItem> convert = trainerSchedule.convert(this.id);
            for (TrainerScheduleItem trainerScheduleItem : convert) {
                this.tableViewTrainings.getItems().add(trainerScheduleItem);
            }
        } catch (IllegalArgumentException e) {
            App.showAlert("Info!", e.getMessage(), Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    public void buttonDeleteTrainerSchedule(ActionEvent actionEvent) {
        try {
            TrainerScheduleItem trainerScheduleItem = (TrainerScheduleItem)
                    this.tableViewTrainings.getSelectionModel().getSelectedItem();
            if (trainerScheduleItem == null) {
                App.showAlert("Info", "Пожалуйста выберите расписание!", Alert.AlertType.INFORMATION);
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
            String dayWeek = map.getOrDefault(trainerScheduleItem.getDayWeek(), "");
            new TrainerScheduleRepository().deleteByIdAndDayWeek
                    (this.id, dayWeek, prefs.get("token", null));
            this.initialize();
        } catch (IllegalArgumentException | IOException e) {
            App.showAlert("Info!", e.getMessage(), Alert.AlertType.INFORMATION);
        }
    }
}
