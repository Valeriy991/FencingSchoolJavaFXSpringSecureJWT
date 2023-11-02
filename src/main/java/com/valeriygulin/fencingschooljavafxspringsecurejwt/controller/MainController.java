package com.valeriygulin.fencingschooljavafxspringsecurejwt.controller;

import com.valeriygulin.fencingschooljavafxspringsecurejwt.App;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.model.Apprentice;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.model.Trainer;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.retrofit.ApprenticeRepository;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.retrofit.TrainerRepository;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;

import java.io.IOException;
import java.util.List;
import java.util.prefs.Preferences;

public class MainController {
    @FXML
    public Label labelWelcome;
    @FXML
    public ListView listViewApprentice;
    @FXML
    public ListView listViewTrainer;
    private Preferences prefs = Preferences.userRoot().node(App.class.getName());


    @FXML
    public void initialize() throws IOException {
        try {
            String login = this.prefs.get("login", "");
            String text = this.labelWelcome.getText();
            if (!text.contains(login)) {
                String res = text + login;
                this.labelWelcome.setText(res);
            }
            List<Apprentice> token = new ApprenticeRepository().getAll(prefs.get("token", ""));
            this.listViewApprentice.setItems(FXCollections.observableList(token));
            this.listViewApprentice.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        Apprentice apprentice = (Apprentice) listViewApprentice.getSelectionModel().getSelectedItem();
                        if (apprentice != null) {
                            try {
                                App.openWindowAndWait("apprenticeForm.fxml", "Форма ученика", apprentice);
                                initialize();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
            });
            this.listViewTrainer.setItems(FXCollections.observableList
                    (new TrainerRepository().getAll(prefs.get("token", ""))));
            this.listViewTrainer.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        Trainer trainer = (Trainer) listViewTrainer.getSelectionModel().getSelectedItem();
                        if (trainer != null) {
                            try {
                                App.openWindowAndWait("trainerForm.fxml", "Форма тренера", trainer);
                                initialize();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void buttonLogOut(ActionEvent actionEvent) throws IOException {
        this.prefs.put("token", "");
        this.prefs.put("login", "");
        this.prefs.put("password", "");
        App.openWindow("authorizationForm.fxml", "Форма авторизации", null);
        App.closeWindow(actionEvent);
    }


    @FXML
    public void buttonAddTrainer(ActionEvent actionEvent) throws IOException {
        App.openWindowAndWait("addFormTrainer.fxml", "Форма добавления тренера ", null);
        this.initialize();
    }

    @FXML
    public void buttonAddApprentice(ActionEvent actionEvent) throws IOException {
        App.openWindowAndWait("addFormApprentice.fxml", "Форма добавления ученика", null);
        this.initialize();
    }
}
