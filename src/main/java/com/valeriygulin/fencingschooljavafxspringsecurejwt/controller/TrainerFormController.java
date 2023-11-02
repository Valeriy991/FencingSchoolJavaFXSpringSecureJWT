package com.valeriygulin.fencingschooljavafxspringsecurejwt.controller;

import com.valeriygulin.fencingschooljavafxspringsecurejwt.App;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.model.Trainer;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.model.TrainerSchedule;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.model.TrainerScheduleItem;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.retrofit.TrainerRepository;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.retrofit.TrainerScheduleRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.prefs.Preferences;

public class TrainerFormController implements ControllerData<Trainer> {
    @FXML
    public TableView<TrainerScheduleItem> tableViewTrainings;
    @FXML
    public TextField textFieldSurname;
    @FXML
    public TextField textFiledPatronymic;
    @FXML
    public TextField textFieldName;
    @FXML
    public TextField textFieldExperience;
    @FXML
    public TableColumn tableColumnDayWeek;
    @FXML
    public TableColumn tableColumnStartTime;
    @FXML
    public TableColumn tableColumnEndTime;
    @FXML
    public TextField textFieldLogin;
    @FXML
    public TextField textFiledPassword;
    private Trainer trainer;
    private Preferences prefs = Preferences.userRoot().node(App.class.getName());
    @Override
    public void initData(Trainer trainer) throws IOException {
        try {
            this.trainer = trainer;
            this.textFieldLogin.setText(trainer.getLogin());
            this.textFiledPassword.setText(trainer.getPassword());
            this.textFieldSurname.setText(trainer.getFirstName());
            this.textFieldName.setText(trainer.getUserName());
            this.textFiledPatronymic.setText(trainer.getLastName());
            this.textFieldExperience.setText(String.valueOf(trainer.getExperience()));
            this.tableColumnDayWeek.setCellValueFactory(new PropertyValueFactory<TrainerScheduleItem, String>("dayWeek"));
            this.tableColumnStartTime.setCellValueFactory(new PropertyValueFactory<TrainerScheduleItem, String>("timeStart"));
            this.tableColumnEndTime.setCellValueFactory(new PropertyValueFactory<TrainerScheduleItem, String>("timeEnd"));
            TrainerSchedule trainerSchedule = new TrainerScheduleRepository().get(trainer.getId(), prefs.get("token", null));
            List<TrainerScheduleItem> convert = trainerSchedule.convert(this.trainer.getId());
            for (TrainerScheduleItem trainerScheduleItem : convert) {
                this.tableViewTrainings.getItems().add(trainerScheduleItem);
            }
        } catch (IllegalArgumentException e) {
            App.showAlert("Info!", e.getMessage(), Alert.AlertType.INFORMATION);
        }
    }
    @FXML
    public void buttonUpdateTrainer(ActionEvent actionEvent) throws IOException {
        String login = this.textFieldLogin.getText();
        if (login.isEmpty()) {
            App.showAlert("Error!", "Введите логин!", Alert.AlertType.ERROR);
        }
        String password = this.textFiledPassword.getText();
        if (password.isEmpty()) {
            App.showAlert("Error!", "Введите пароль!", Alert.AlertType.ERROR);
        }
        String firstName = this.textFieldSurname.getText();
        if (firstName.isEmpty()) {
            App.showAlert("Error!", "Введите фамилию!", Alert.AlertType.ERROR);
            return;
        }
        String userName = this.textFieldName.getText();
        if (userName.isEmpty()) {
            App.showAlert("Error!", "Введите имя!", Alert.AlertType.ERROR);
            return;
        }
        String lastName = this.textFiledPatronymic.getText();
        if (lastName.isEmpty()) {
            App.showAlert("Error!", "Введите отчество!", Alert.AlertType.ERROR);
            return;
        }
        String expereince = this.textFieldExperience.getText();
        if (expereince.isEmpty()) {
            App.showAlert("Error!", "Введите опыт работы!", Alert.AlertType.ERROR);
            return;
        }
        try {
            Trainer put = new TrainerRepository().put(new Trainer(
                            this.trainer.getId(),
                            this.trainer.getCreated(),
                            this.trainer.getUpdated(),
                            login,
                            password,
                            firstName,
                            userName,
                            lastName,
                            this.trainer.getRole(),
                            this.trainer.getStatus(),
                            Integer.parseInt(expereince)),
                    prefs.get("token", null));
            App.showAlert("Info!", put.getUserName() + " был обновлен!", Alert.AlertType.INFORMATION);
        } catch (IllegalArgumentException | NullPointerException e) {
            App.showAlert("Info!", e.getMessage(), Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    public void buttonDeleteTrainer(ActionEvent actionEvent) throws IOException {
        try {
            new TrainerRepository().delete(this.trainer.getId(), prefs.get("token", null));
            App.showAlert("Info", "Trainer " + this.trainer.getUserName() +
                    " has been deleted!", Alert.AlertType.INFORMATION);
            App.closeWindow(actionEvent);
        } catch (NullPointerException | IllegalArgumentException e) {
            App.showAlert("Info!", "Нет права доступа!", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    public void ButtonAddTrainerSchedule(ActionEvent actionEvent) throws IOException {
        App.openWindowAndWait("trainerScheduleForm.fxml", "Форма добавления тренера", this.trainer);
        this.tableViewTrainings.getItems().clear();
        this.initData(this.trainer);
    }

    @FXML
    public void buttonDeleteTrainerSchedule(ActionEvent actionEvent) throws IOException {
        try {
            TrainerScheduleItem trainerScheduleItem = this.tableViewTrainings.getSelectionModel().getSelectedItem();
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
            try {
                String dayWeek = map.getOrDefault(trainerScheduleItem.getDayWeek(), "");
                new TrainerScheduleRepository().deleteByIdAndDayWeek(this.trainer.getId(), dayWeek, prefs.get("token", null));
                this.initData(this.trainer);
            } catch (NullPointerException e) {
                App.showAlert("Info!", "Нет права доступа!", Alert.AlertType.INFORMATION);
            }
        } catch (IllegalArgumentException e) {
            App.showAlert("Info!", e.getMessage(), Alert.AlertType.INFORMATION);
        }
    }


}
