package com.valeriygulin.fencingschooljavafxspringsecurejwt;

import com.valeriygulin.fencingschooljavafxspringsecurejwt.controller.ControllerData;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.retrofit.AuthRepository;
import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.prefs.Preferences;

public class App extends Application {
    private AuthRepository authRepository = new AuthRepository();
    private Preferences prefs = Preferences.userRoot().node(App.class.getName());

    @Override
    public void start(Stage stage) throws IOException {
        String str = prefs.get("token", "");
        String token = str.replace("Bearer_", "");
        boolean tokenIsAlive = false;
        if (!token.isEmpty()) {
            try {
                tokenIsAlive = this.authRepository.tokenIsAlive(token);
            } catch (IOException | IllegalArgumentException e) {
                App.showAlert("Error!", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
        if (prefs.get("login", "").isEmpty() && prefs.get("password", "").isEmpty()
                || !tokenIsAlive) {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("authorizationForm.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 280, 280);
            stage.setTitle("Форма авторизации");
            stage.setScene(scene);
            stage.show();
        } else {
            String role = prefs.get("role", "");
            if (role.equals("ROLE_ADMIN")) {
                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("mainForm.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 945, 410);
                stage.setTitle("Главное меню");
                stage.setScene(scene);
                stage.show();
            } else if (role.equals("ROLE_APPRENTICE")) {
                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("mainFormForApprentice.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 785, 455);
                stage.setTitle("Главное меню");
                stage.setScene(scene);
                stage.show();
            } else if (role.equals("ROLE_TRAINER")) {
                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("mainFormForTrainer.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 670, 560);
                stage.setTitle("Главное меню");
                stage.setScene(scene);
                stage.show();
            }
        }

    }

    public static void main(String[] args) {
        launch();
    }

    public static void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static <T> Stage getStage(String name, String title, T data) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource(name));
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene(loader.load()));
        stage.setTitle(title);
        if (data != null) {
            ControllerData<T> controller = loader.getController();
            controller.initData(data);
        }
        return stage;
    }

    public static <T> Stage openWindow(String name, String title, T data) throws IOException {
        Stage stage = getStage(name, title, data);
        stage.show();
        return stage;
    }

    public static <T> Stage openWindowAndWait(String name, String title, T data) throws IOException {
        Stage stage = getStage(name, title, data);
        stage.showAndWait();
        return stage;
    }

    public static void closeWindow(Event event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}