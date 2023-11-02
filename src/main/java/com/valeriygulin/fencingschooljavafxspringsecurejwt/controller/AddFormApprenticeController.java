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

public class AddFormApprenticeController {
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
    public TextField textFieldPhoneNumber;

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
            App.showAlert("Error!", "Enter surname!", Alert.AlertType.ERROR);
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
        String phoneNumber = textFieldPhoneNumber.getText();
        if (phoneNumber.isEmpty()) {
            App.showAlert("Error!", "Enter phone number!", Alert.AlertType.ERROR);
            return;
        }
        Apprentice apprentice1 = new Apprentice(login, password, firstName, userName, lastName, phoneNumber,
                new Role("ROLE_APPRENTICE"));
        Apprentice apprentice = new ApprenticeRepository().post(apprentice1);
        App.showAlert("Info", "Ученик " + apprentice.getUserName() +
                " был добавлен!", Alert.AlertType.INFORMATION);
        App.closeWindow(actionEvent);
    }
}
