package com.mirald.presentation.controller;

import static com.mirald.presentation.Runner.springContext;

import com.mirald.domain.exception.AuthenticationException;
import com.mirald.domain.exception.UserAlreadyAuthenticatedException;
import com.mirald.domain.service.impl.AuthenticationService;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component
public class SignInController {

    private final AuthenticationService authenticationService;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button exitButton;


    public SignInController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @FXML
    public void initialize() {

    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void signIn() {
        String login = usernameField.getText();
        String password = passwordField.getText();

        try {
            boolean authenticated = authenticationService.authenticate(login, password);

            if (authenticated) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mirald/presentation/view/main.fxml"));
                loader.setControllerFactory(springContext::getBean);
                Parent root = loader.load();
                MainController mainController = loader.getController();

                mainController.setCurrentUser(login);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/mirald/presentation/icon.png")));
                stage.setTitle("Main window");
                stage.show();

                Stage currentStage = (Stage) usernameField.getScene().getWindow();
                currentStage.close();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Incorrect login or password.");
            }
        } catch (UserAlreadyAuthenticatedException e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        } catch (AuthenticationException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Incorrect login or password.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    private void goToRegistration(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/com/mirald/presentation/view/signUp.fxml"));
            loader.setControllerFactory(springContext::getBean);
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image(getClass()
                .getResourceAsStream("/com/mirald/presentation/icon.png")));
            stage.setTitle("Registration");

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.initOwner(((Node) event.getSource()).getScene().getWindow());

            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void exit() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}
