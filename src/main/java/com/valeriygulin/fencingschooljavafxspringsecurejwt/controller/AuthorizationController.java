package com.valeriygulin.fencingschooljavafxspringsecurejwt.controller;

import com.valeriygulin.fencingschooljavafxspringsecurejwt.App;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.retrofit.AuthRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.ConnectException;
import java.util.Map;
import java.util.prefs.Preferences;

public class AuthorizationController {
    @FXML
    public TextField textFieldLogin;
    @FXML
    public TextField textFieldPassword;
    @FXML
    public CheckBox checkBox;
    @FXML
    public PasswordField passwordField;

    private AuthRepository authRepository = new AuthRepository();

    private Preferences prefs = Preferences.userRoot().node(App.class.getName());


    @FXML
    public void initialize() {
        this.togglevisiblePassword(null);
    }

    @FXML
    public void togglevisiblePassword(ActionEvent event) {
        if (checkBox.isSelected()) {
            textFieldPassword.setText(passwordField.getText());
            textFieldPassword.setVisible(true);
            passwordField.setVisible(false);
            return;
        }
        passwordField.setText(textFieldPassword.getText());
        passwordField.setVisible(true);
        textFieldPassword.setVisible(false);
    }

    @FXML
    public void buttonLogIn(ActionEvent actionEvent) throws IOException {
        String login = textFieldLogin.getText();
        if (login.isEmpty()) {
            App.showAlert("Error!", "Введите логин!", Alert.AlertType.ERROR);
            return;
        }
        prefs.put("login", login);
        String password = textFieldPassword.getText();
        String password2 = passwordField.getText();
        if (password.isEmpty()) {
            if (password2.isEmpty()) {
                App.showAlert("Error!", "Введите пароль!", Alert.AlertType.ERROR);
                return;
            } else {
                prefs.put("password", password2);
            }
        } else {
            prefs.put("password", password);
        }
        String login1 = this.prefs.get("login", "");
        String password1 = this.prefs.get("password", "");
        Map<String, String> post = null;
        try {
            post = this.authRepository.post(login1, password1);
            if (post.isEmpty()) {
                App.showAlert("Error!", "Вы не зарегистрированы!", Alert.AlertType.ERROR);
            } else {
                for (Map.Entry<String, String> entry : post.entrySet()) {
                    if (entry.getKey().equals("userName")) {
                        this.prefs.put("userName", entry.getValue());
                    } else if (entry.getKey().equals("token")) {
                        String res = "Bearer_" + entry.getValue();
                        this.prefs.put("token", res);
                    } else if (entry.getKey().equals("id")) {
                        this.prefs.put("id", entry.getValue());
                    } else if (entry.getKey().equals("role")) {
                        this.prefs.put("role", entry.getValue());
                    }
                }
                String role = prefs.get("role", "");
                if (role.equals("ROLE_APPRENTICE")) {
                    App.openWindow("mainFormForApprentice.fxml", "Главное меню", null);
                } else if (role.equals("ROLE_TRAINER")) {
                    App.openWindow("mainFormForTrainer.fxml", "Главное меню", null);
                } else if (role.equals("ROLE_ADMIN")) {
                    App.openWindow("mainForm.fxml", "Главное меню", null);
                }
                App.closeWindow(actionEvent);
            }
        } catch (IllegalArgumentException e) {
            App.showAlert("Error!", e.getMessage(), Alert.AlertType.ERROR);
        } catch (ConnectException e) {
            App.showAlert("Error!", "Нет подключения к серверу!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void buttonRegistration(ActionEvent actionEvent) throws IOException {
        App.openWindow("registrationForm.fxml", "Форма регистрации", null);
        App.closeWindow(actionEvent);
    }
}
