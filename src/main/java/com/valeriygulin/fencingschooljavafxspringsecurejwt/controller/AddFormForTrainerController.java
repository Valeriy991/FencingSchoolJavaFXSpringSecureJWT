package com.valeriygulin.fencingschooljavafxspringsecurejwt.controller;

import com.valeriygulin.fencingschooljavafxspringsecurejwt.App;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.model.Role;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.model.Trainer;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.retrofit.TrainerRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.prefs.Preferences;

public class AddFormForTrainerController {
    @FXML
    public TextField textFieldLogin;
    @FXML
    public TextField textFieldPassword;
    @FXML
    public TextField textFieldSurname;
    @FXML
    public TextField textFieldName;
    @FXML
    public TextField textFieldPatronymic;
    @FXML
    public TextField textFieldExp;

    private Preferences prefs = Preferences.userRoot().node(App.class.getName());

    @FXML
    public void buttonAdd(ActionEvent actionEvent) throws IOException {
        String login = this.textFieldLogin.getText();
        if (login.isEmpty()) {
            App.showAlert("Error!", "Введите логин!", Alert.AlertType.ERROR);
        }
        String password = this.textFieldPassword.getText();
        if (password.isEmpty()) {
            App.showAlert("Error!", "Введите пароль!", Alert.AlertType.ERROR);
        }
        String firstName = textFieldSurname.getText();
        if (firstName.isEmpty()) {
            App.showAlert("Error!", "Введите фамилию!", Alert.AlertType.ERROR);
            return;
        }
        String userName = textFieldName.getText();
        if (userName.isEmpty()) {
            App.showAlert("Error!", "Enter name!", Alert.AlertType.ERROR);
            return;
        }
        String lastName = textFieldPatronymic.getText();
        if (lastName.isEmpty()) {
            App.showAlert("Error!", "Enter patronymic!", Alert.AlertType.ERROR);
            return;
        }
        String experience = textFieldExp.getText();
        if (experience.isEmpty()) {
            App.showAlert("Error!", "Enter experience!", Alert.AlertType.ERROR);
            return;
        }
        try {
            Trainer trainer = new TrainerRepository().
                    post(new Trainer(login, password, firstName, userName, lastName,
                                    new Role("ROLE_TRAINER"), Integer.parseInt(experience)),
                            this.prefs.get("token", ""));
            App.showAlert("Info", "Trainer " + trainer.getUserName() + " has been added!",
                    Alert.AlertType.INFORMATION);
            App.closeWindow(actionEvent);
        } catch (IllegalArgumentException e) {
            App.showAlert("Error!", e.getMessage(), Alert.AlertType.ERROR);
        } catch (NullPointerException ex) {
            App.showAlert("Error!", "Нет права доступа!", Alert.AlertType.ERROR);
        }
    }
}
