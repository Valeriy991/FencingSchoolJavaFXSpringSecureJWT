package com.valeriygulin.fencingschooljavafxspringsecurejwt.controller;

import com.valeriygulin.fencingschooljavafxspringsecurejwt.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class RegistrationController {

    @FXML
    public void buttonCreateAdmin(ActionEvent actionEvent) throws IOException {
        App.openWindowAndWait("adminRegForm.fxml", "Регистрация администратора", null);
        App.closeWindow(actionEvent);
    }

    @FXML
    public void buttonCreateApprentice(ActionEvent actionEvent) throws IOException {
        App.openWindowAndWait("apprenticeRegForm.fxml", "Регистрация ученика", null);
        App.closeWindow(actionEvent);
    }


}