package com.mirald.presentation.controller;

import com.mirald.persistence.util.ConnectionManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javafx.scene.control.ListCell;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class DeleteElementsController {

    @FXML
    private CheckBox titleCheckBox;

    @FXML
    private CheckBox authorsCheckBox;

    @FXML
    private CheckBox storyCheckBox;

    @FXML
    private ListView<String> dataListView;

    private final ConnectionManager connectionManager;

    @Autowired
    public DeleteElementsController(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
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
    public void initialize() {

        titleCheckBox.setOnAction(event -> updateCheckBoxStyle(titleCheckBox));
        authorsCheckBox.setOnAction(event -> updateCheckBoxStyle(authorsCheckBox));
        storyCheckBox.setOnAction(event -> updateCheckBoxStyle(storyCheckBox));

        dataListView.setCellFactory(param -> new ListCell<String>() {

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("-fx-background-color: transparent;");
                } else {
                    setText(item);
                    setStyle("-fx-background-color: #6D5D6E; -fx-font-weight: bold; -fx-font-family: Bahnschrift-Light; -fx-text-fill: #F4EEE0; -fx-font-size: 16;");
                }
            }
        });
    }

    @FXML
    private void loadData() {
        dataListView.getItems().clear();

        if (titleCheckBox.isSelected()) {
            loadTitle();
        } else if (authorsCheckBox.isSelected()) {
            loadAuthors();
        } else if (storyCheckBox.isSelected()) {
            loadStory();
        } else {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please select the type of item to upload data.");
        }
    }

    private void loadTitle() {
        try (Connection connection = connectionManager.get();
             PreparedStatement statement = connection.prepareStatement("SELECT name FROM title")) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                dataListView.getItems().add(name);
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error loading titles: " + e.getMessage());
        }
    }

    private void loadAuthors() {
        try (Connection connection = connectionManager.get();
             PreparedStatement statement = connection.prepareStatement("SELECT name FROM authors")) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                dataListView.getItems().add(name);
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error uploading author: " + e.getMessage());
        }
    }

    private void loadStory() {
        try (Connection connection = connectionManager.get();
             PreparedStatement statement = connection.prepareStatement("SELECT name FROM story")) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                dataListView.getItems().add(name);
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error loading story: " + e.getMessage());
        }
    }

    @FXML
    private void deleteSelected() {
        String selectedItem = dataListView.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            showAlert(Alert.AlertType.WARNING, "Попередження", "Будь ласка, виберіть елемент для видалення.");
            return;
        }

        if (titleCheckBox.isSelected()) {
            deleteTitle(selectedItem);
        } else if (authorsCheckBox.isSelected()) {
            deleteAuthors(selectedItem);
        } else if (storyCheckBox.isSelected()) {
            deleteStory(selectedItem);
        } else {
            showAlert(Alert.AlertType.WARNING, "Попередження", "Будь ласка, виберіть тип елементу для видалення.");
        }
    }

    private void deleteTitle(String name) {
        try (Connection connection = connectionManager.get();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM title WHERE name = ?")) {
            statement.setString(1, name);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Name successfully deleted.");
                loadData();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Error deleting a name.");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error deleting a name: " + e.getMessage());
        }
    }

    private void deleteAuthors(String name) {
        try (Connection connection = connectionManager.get();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM authors WHERE name = ?")) {
            statement.setString(1, name);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "The author has been successfully removed.");
                loadData();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Error when deleting an author.");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error when deleting an author: " + e.getMessage());
        }
    }

    private void deleteStory(String name) {
        try (Connection connection = connectionManager.get();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM story WHERE name = ?")) {
            statement.setString(1, name);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Story successfully deleted.");
                loadData();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Error deleting a story.");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error deleting a story: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
