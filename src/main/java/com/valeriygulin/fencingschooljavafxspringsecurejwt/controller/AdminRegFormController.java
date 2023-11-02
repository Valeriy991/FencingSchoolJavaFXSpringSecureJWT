package com.valeriygulin.fencingschooljavafxspringsecurejwt.controller;

import com.valeriygulin.fencingschooljavafxspringsecurejwt.App;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.model.Admin;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.model.Role;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.retrofit.AdminRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AdminRegFormController {
    private AdminRepository adminRepository = new AdminRepository();
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
    public void buttonRegAdmin(ActionEvent actionEvent) {
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
        Admin admin = new Admin(firstName, userName, lastName, login, password, new Role(null, "ROLE_ADMIN"));
        System.out.println(firstName + " " + lastName + " " + userName + " " + login + " " + password + " " + admin.getRole());
        try {
            Admin post = adminRepository.post(admin);
            App.showAlert("Info!", "Аккаунт администратора " + post.getUserName() +
                    " добавлен", Alert.AlertType.INFORMATION);
            App.openWindow("authorizationForm.fxml", "Форма авторизации", null);
            App.closeWindow(actionEvent);
        } catch (IOException | IllegalArgumentException e) {
            App.showAlert("Error!", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
