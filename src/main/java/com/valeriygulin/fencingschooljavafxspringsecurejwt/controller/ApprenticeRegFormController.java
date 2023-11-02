package com.valeriygulin.fencingschooljavafxspringsecurejwt.controller;

import com.valeriygulin.fencingschooljavafxspringsecurejwt.App;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.model.Apprentice;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.model.Role;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.retrofit.ApprenticeRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ApprenticeRegFormController {
    private ApprenticeRepository apprenticeRepository = new ApprenticeRepository();
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
    public TextField textFieldPhoneNum;

    public ApprenticeRegFormController() {
    }

    @FXML
    public void buttonRegApprentice(ActionEvent actionEvent) {
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
        String phoneNum = this.textFieldPhoneNum.getText();
        if (phoneNum.isEmpty()) {
            App.showAlert("Error!", "Введите номер телефона!", Alert.AlertType.ERROR);
        }
        Apprentice apprentice = new Apprentice(login, password, firstName,
                userName, lastName, phoneNum, new Role("ROLE_APPRENTICE"));
        try {
            Apprentice post = this.apprenticeRepository.post(apprentice);
            App.showAlert("Info!", "Аккаунт ученика " + post.getUserName() + " добавлен!",
                    Alert.AlertType.INFORMATION);
            App.openWindow("authorizationForm.fxml", "Форма авторизации", null);
            App.closeWindow(actionEvent);
        } catch (IOException e) {
            e.printStackTrace();
            App.showAlert("Error!", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
