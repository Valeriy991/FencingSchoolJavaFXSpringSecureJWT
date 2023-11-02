package com.valeriygulin.fencingschooljavafxspringsecurejwt.controller;

import com.valeriygulin.fencingschooljavafxspringsecurejwt.App;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.model.Apprentice;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.model.Role;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.model.Training;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.retrofit.ApprenticeRepository;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.retrofit.TrainingRepository;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;
import java.util.prefs.Preferences;

public class ApprenticeFormController implements ControllerData<Apprentice> {
    @FXML
    public TextField textFieldSurname;
    @FXML
    public TextField textFieldName;
    @FXML
    public TextField textFiledPatronymic;
    @FXML
    public TextField textFieldPhoneNum;
    @FXML
    public ListView listViewTrainings;
    @FXML
    public TextField textFieldPassword;

    @FXML
    public TextField textFieldLogin;
    private Apprentice apprentice;
    private Preferences prefs = Preferences.userRoot().node(App.class.getName());


    @Override
    public void initData(Apprentice apprentice) {
        this.apprentice = apprentice;
        this.textFieldLogin.setText(apprentice.getUserName());
        this.textFieldPassword.setText(apprentice.getPassword());
        this.textFieldSurname.setText(apprentice.getFirstName());
        this.textFieldName.setText(apprentice.getUserName());
        this.textFiledPatronymic.setText(apprentice.getLastName());
        this.textFieldPhoneNum.setText(apprentice.getPhoneNumber());
        try {
            List<Training> byApprentice = new TrainingRepository().
                    getByApprentice(apprentice.getId(), this.prefs.get("token", ""));
            this.listViewTrainings.setItems(FXCollections.observableList(byApprentice));
        } catch (IllegalArgumentException | IOException e) {
            App.showAlert("Info!", e.getMessage(), Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    public void buttonUpdateApprentice(ActionEvent actionEvent) throws IOException {
        String login = this.textFieldLogin.getText();
        if (login.isEmpty()) {
            App.showAlert("Error!", "Введите логин!", Alert.AlertType.ERROR);
        }
        String password = this.textFieldPassword.getText();
        if (password.isEmpty()) {
            App.showAlert("Error!", "Введите пароль!", Alert.AlertType.ERROR);
        }
        String firstName = this.textFieldSurname.getText();
        if (firstName == null) {
            App.showAlert("Error!", "Enter surname!", Alert.AlertType.ERROR);
            return;
        }
        String userName = this.textFieldName.getText();
        if (userName == null) {
            App.showAlert("Error!", "Enter name!", Alert.AlertType.ERROR);
            return;
        }
        String lastName = this.textFiledPatronymic.getText();
        if (lastName == null) {
            App.showAlert("Error!", "Enter patronymic!", Alert.AlertType.ERROR);
            return;
        }
        String phoneNum = this.textFieldPhoneNum.getText();
        if (phoneNum == null) {
            App.showAlert("Error!", "Enter phone number!", Alert.AlertType.ERROR);
            return;
        }
        try {
            this.apprentice = new ApprenticeRepository().put
                    (new Apprentice(this.apprentice.getId(),
                            this.apprentice.getCreated(),
                            this.apprentice.getUpdated(),
                            login,
                            password,
                            firstName,
                            userName,
                            lastName,
                            new Role("ROLE_APPRENTICE"),
                            this.apprentice.getStatus(),
                            phoneNum), this.prefs.get("token", ""));
        } catch (IllegalArgumentException e) {
            App.showAlert("Info", e.getMessage(), Alert.AlertType.INFORMATION);
        }
        App.showAlert("Info", "Apprentice " + this.apprentice.getUserName() +
                " has been updated!", Alert.AlertType.INFORMATION);
        this.initData(this.apprentice);
    }

    @FXML
    public void buttonDeleteApprentice(ActionEvent actionEvent) throws IOException {
        try {
            Apprentice delete = new ApprenticeRepository().
                    delete(this.apprentice.getId(),
                            prefs.get("token", ""));
            App.showAlert("Info", "Apprentice " + delete.getUserName() +
                    " has been deleted!", Alert.AlertType.INFORMATION);
            App.closeWindow(actionEvent);
        } catch (IllegalArgumentException e) {
            App.showAlert("Info", e.getMessage(), Alert.AlertType.INFORMATION);
        }

    }

    @FXML
    public void ButtonAddTraining(ActionEvent actionEvent) throws IOException {
        App.openWindowAndWait("trainingAddForm.fxml", "Форма добавления тренировки", this.apprentice);
        this.initData(this.apprentice);
    }

    @FXML
    public void buttonDeleteTraining(ActionEvent actionEvent) throws IOException {
        Training training = (Training) this.listViewTrainings.getSelectionModel().getSelectedItem();
        if (training == null) {
            App.showAlert("Info", "Please choose training", Alert.AlertType.INFORMATION);
            return;
        }
        new TrainingRepository().delete(training.getId(), this.prefs.get("token", ""));
        this.initData(this.apprentice);
    }


}
