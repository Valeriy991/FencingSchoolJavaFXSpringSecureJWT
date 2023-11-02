package com.valeriygulin.fencingschooljavafxspringsecurejwt.controller;

import com.valeriygulin.fencingschooljavafxspringsecurejwt.App;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.model.Training;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.retrofit.TrainerRepository;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.retrofit.TrainingRepository;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.util.List;
import java.util.prefs.Preferences;

public class MainFormForApprenticeController {
    @FXML
    public ListView listViewTrainings;
    @FXML
    public Label labelWelcome;

    private TrainingRepository trainingRepository = new TrainingRepository();
    private Preferences prefs = Preferences.userRoot().node(App.class.getName());

    @FXML
    public void initialize() {
        try {
            this.listViewTrainings.getItems().clear();
            String login = this.prefs.get("login", "");
            String text = this.labelWelcome.getText();
            if (!text.contains(login)) {
                String res = text + login;
                this.labelWelcome.setText(res);
            }
            long id = Long.parseLong(prefs.get("id", ""));
            List<Training> byApprentice = new TrainingRepository().
                    getByApprentice(id, this.prefs.get("token", ""));
            this.listViewTrainings.setItems(FXCollections.observableList(byApprentice));
        } catch (IllegalArgumentException | IOException e) {
            App.showAlert("Info!", e.getMessage(), Alert.AlertType.INFORMATION);
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
    public void buttonAddApprentice(ActionEvent actionEvent) throws IOException {
        App.openWindowAndWait("addFormApprentice.fxml", "Форма добавления ученика", null);
        this.initialize();
    }

    @FXML
    public void ButtonAddTraining(ActionEvent actionEvent) throws IOException {
        App.openWindowAndWait("trainingAddForm.fxml", "Форма добавления тренировки", null);
        this.initialize();
    }

    @FXML
    public void buttonDeleteTraining(ActionEvent actionEvent) throws IOException {
        Training training = (Training) this.listViewTrainings.getSelectionModel().getSelectedItem();
        if (training == null) {
            App.showAlert("Info", "Please choose training", Alert.AlertType.INFORMATION);
            return;
        }
        this.trainingRepository.delete(training.getId(), this.prefs.get("token", ""));
        App.showAlert("Info", "Тренировка " + training.getId() + " удалена успешно!", Alert.AlertType.INFORMATION);
        this.initialize();
    }
}
