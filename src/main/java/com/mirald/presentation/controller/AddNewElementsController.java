package com.mirald.presentation.controller;

import com.mirald.persistence.util.ConnectionManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class AddNewElementsController {

    @FXML
    private CheckBox storyCheckBox;

    @FXML
    private CheckBox titleCheckBox;

    @FXML
    private CheckBox authorsCheckBox;

    @FXML
    private TextField textField1;

    @FXML
    private TextField textField2;

    @FXML
    private TextField textField3;

    private final ConnectionManager connectionManager;

    @Autowired
    public AddNewElementsController(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @FXML
    public void initialize() {
        titleCheckBox.setOnAction(event -> {
            updateCheckBoxStyle(titleCheckBox);
            onCheckBoxAction();
        });
        authorsCheckBox.setOnAction(event -> {
            updateCheckBoxStyle(authorsCheckBox);
            onCheckBoxAction();
        });
        storyCheckBox.setOnAction(event -> {
            updateCheckBoxStyle(storyCheckBox);
            onCheckBoxAction();
        });
    }

    private void updateCheckBoxStyle(CheckBox checkBox) {
        if (checkBox.isSelected()) {
            checkBox.setStyle("-fx-font-family: Bahnschrift-Light; -fx-text-fill: #F4EEE0; -fx-font-size: 14;");
            checkBox.lookup(".box").setStyle("-fx-background-color: #413947; -fx-border-color: #413947;");
        } else {
            checkBox.setStyle("-fx-font-family: Bahnschrift-Light; -fx-text-fill: #F4EEE0; -fx-font-size: 14;");
            checkBox.lookup(".box").setStyle("");
        }
    }

    @FXML
    public void onCheckBoxAction() {
        if (authorsCheckBox.isSelected()) {
            textField2.setDisable(true);
            textField3.setDisable(false);
        } else {
            textField2.setDisable(false);
            textField3.setDisable(true);
        }
    }

    @FXML
    public void onSave() {
        if (storyCheckBox.isSelected()) {
            saveStory();
        } else if (titleCheckBox.isSelected()) {
            saveTitle();
        } else if (authorsCheckBox.isSelected()) {
            saveAuthors();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Select the type of item to save");
        }
    }

    private void saveTitle() {
        try (Connection connection = connectionManager.get();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO title (id, name, description) VALUES (?, ?, ?)")) {
            UUID id = UUID.randomUUID();
            String name = textField1.getText();
            String description = textField2.getText();

            statement.setObject(1, id);
            statement.setString(2, name);
            statement.setString(3, description);
            statement.executeUpdate();

            showAlert(Alert.AlertType.INFORMATION, "Success", "Name successfully saved!");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error saving the name: " + e.getMessage());
        }
    }

    private void saveAuthors() {
        try (Connection connection = connectionManager.get();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO authors (id, name, address) VALUES (?, ?, ?)")) {
            UUID id = UUID.randomUUID();
            String name = textField1.getText();
            String address = textField3.getText();

            statement.setObject(1, id);
            statement.setString(2, name);
            statement.setString(3, address);
            statement.executeUpdate();

            showAlert(Alert.AlertType.INFORMATION, "Success", "Author successfully saved!");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error saving the author: " + e.getMessage());
        }
    }

    private void saveStory() {
        try (Connection connection = connectionManager.get();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO story (id, name, description) VALUES (?, ?, ?)")) {
            UUID id = UUID.randomUUID();
            String name = textField1.getText();
            String description = textField2.getText();

            statement.setObject(1, id);
            statement.setString(2, name);
            statement.setString(3, description);
            statement.executeUpdate();

            showAlert(Alert.AlertType.INFORMATION, "Success", "Story successfully saved!");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error saving a story: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void onCancel() {
        Stage currentStage = (Stage) textField1.getScene().getWindow();
        currentStage.close();
    }
}
