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
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

public class TrainerRegFormController {
    private TrainerRepository trainerRepository = new TrainerRepository();
    @FXML
    public TextField textFieldLogin;
    @FXML
    public TextField textFieldPassword;
    @FXML
    public TextField textFieldFirstName;
    @FXML
    public TextField textFieldUserName;
    @FXML
    public TextField textFieldLastName;
    @FXML
    public TextField textFieldExp;
    private Preferences prefs = Preferences.userRoot().node(App.class.getName());

    @FXML
    public void buttonRegTrainer(ActionEvent actionEvent) {
        String login = this.textFieldLogin.getText();
        if (login.isEmpty()) {
            App.showAlert("Error!", "Введите логин!", Alert.AlertType.ERROR);
        }
        String password = this.textFieldPassword.getText();
        if (password.isEmpty()) {
            App.showAlert("Error!", "Введите пароль!", Alert.AlertType.ERROR);
        }
        String firstName = this.textFieldFirstName.getText();
        if (firstName.isEmpty()) {
            App.showAlert("Error!", "Введите фамилию!", Alert.AlertType.ERROR);
        }
        String userName = this.textFieldUserName.getText();
        if (userName.isEmpty()) {
            App.showAlert("Error!", "Введите имя!", Alert.AlertType.ERROR);
        }
        String lastName = this.textFieldLastName.getText();
        if (lastName.isEmpty()) {
            App.showAlert("Error!", "Введите отчество!", Alert.AlertType.ERROR);
        }
        String exp = this.textFieldExp.getText();
        if (exp.isEmpty()) {
            App.showAlert("Error!", "Введите номер телефона!", Alert.AlertType.ERROR);
        }
        Trainer trainer = new Trainer(login,
                password, firstName, userName, lastName, new Role("ROLE_TRAINER"), Integer.parseInt(exp));
        try {
            Trainer post = this.trainerRepository.post(trainer, this.prefs.get("token", ""));
            App.showAlert("Info!", "Аккаунт тренера  " + post.getUserName() + " добавлен!", Alert.AlertType.INFORMATION);
            App.openWindow("authorizationForm.fxml", "Форма авторизации", null);
            App.closeWindow(actionEvent);
        } catch (NullPointerException|IOException e) {
            App.showAlert("Error!","Нет права доступа!", Alert.AlertType.ERROR);
        }
    }
}
