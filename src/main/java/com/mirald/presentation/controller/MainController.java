package com.mirald.presentation.controller;

import static com.mirald.presentation.Runner.springContext;

import com.mirald.persistence.util.ConnectionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

@Component
public class MainController {

    @FXML
    private Label userLabel;

    @FXML
    private Label accessLevelLabel;

    @FXML
    private ListView<String> ListView;

    @FXML
    private Button deleteButton;

    @FXML
    private Button addButton;

    private String login;
    private String userId;
    protected UUID idUser;
    private String role;

    private Connection connection;

    private final ConnectionManager connectionManager;

    @Autowired
    public MainController(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @FXML
    public void initialize() {
        setConnection(connection);
        retrieveUserData();

        ListView.setCellFactory(param -> new ListCell<String>() {

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("-fx-background-color: transparent;");
                } else {
                    setText(item.split(" - ")[0]);
                    setStyle("-fx-background-color: transparent; -fx-font-weight: bold; -fx-font-family: Bahnschrift-Light; -fx-text-fill: #F4EEE0; -fx-font-size: 16;");
                }
            }
        });

        ListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !ListView.getSelectionModel().isEmpty()) {
                String selectedItem = ListView.getSelectionModel().getSelectedItem();
                String[] parts = selectedItem.split(" - ", 2);
                String itemName = parts[0];
                String itemDetails = (parts.length > 1) ? parts[1] : "";
                openInfoWindow(itemName, itemDetails);
            }
        });
    }

    public void setCurrentUser(String login) {
        this.login = login;
        retrieveUserData();
    }

    @FXML
    private void showAddButtons(String role) {
        if (role != null && (role.equals("moder") || role.equals("admin"))) {
            addButton.setVisible(true);
        } else {
            addButton.setVisible(false);
        }
    }

    @FXML
    private void showDeleteButtons(String role) {
        if (role != null && (role.equals("moder") || role.equals("admin"))) {
            deleteButton.setVisible(true);
        } else {
            deleteButton.setVisible(false);
        }
    }

    private void retrieveUserData() {
        try {
            if (login != null) {
                Statement statement = connection.createStatement();
                ResultSet userResultSet = statement.executeQuery(
                        "SELECT * FROM users WHERE login = '" + login + "'");
                if (userResultSet.next()) {
                    String loginCurrentUser = userResultSet.getString("login");
                    String roleCurrenUser = userResultSet.getString("role");

                    UUID idUserForCart = (UUID) userResultSet.getObject("id");
                    this.idUser = idUserForCart;

                    userLabel.setText("User: " + loginCurrentUser);
                    accessLevelLabel.setText("Access: " + roleCurrenUser);
                    showAddButtons(roleCurrenUser);
                    showDeleteButtons(roleCurrenUser);
                    this.role = roleCurrenUser;
                    userId = userResultSet.getString("id");
                } else {
                    userLabel.setText("User not found");
                    accessLevelLabel.setText("");
                }
                statement.close();
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private void loadTitleData() {
        try {
            ObservableList<String> data = FXCollections.observableArrayList();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT name, description FROM title");

            while (resultSet.next()) {
                String itemName = resultSet.getString("name");
                String itemDescription = resultSet.getString("description");
                data.add(itemName + " - " + itemDescription);
            }

            ListView.setItems(data);

            statement.close();
            resultSet.close();

        } catch (SQLException e) {
            throw new RuntimeException("Error loading data into ListView", e);
        }
    }

    private void loadAuthorsData() {
        try {
            ObservableList<String> data = FXCollections.observableArrayList();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT name, address FROM authors");

            while (resultSet.next()) {
                String itemName = resultSet.getString("name");
                String itemAddress = resultSet.getString("address");
                data.add(itemName + " - " + itemAddress);
            }

            ListView.setItems(data);

            statement.close();
            resultSet.close();

        } catch (SQLException e) {
            throw new RuntimeException("Error loading data into ListView", e);
        }
    }

    private void loadStoryData() {
        try {
            ObservableList<String> data = FXCollections.observableArrayList();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT name, description FROM story");

            while (resultSet.next()) {
                String itemName = resultSet.getString("name");
                String itemDescription = resultSet.getString("description");
                data.add(itemName + " - " + itemDescription);
            }

            ListView.setItems(data);

            statement.close();
            resultSet.close();

        } catch (SQLException e) {
            throw new RuntimeException("Error loading data into ListView", e);
        }
    }

    @FXML
    private void titleButtonClicked(ActionEvent event) {
        loadTitleData();
    }

    @FXML
    private void authorsButtonClicked(ActionEvent event) {
        loadAuthorsData();
    }

    @FXML
    private void storyButtonClicked(ActionEvent event) {
        loadStoryData();
    }

    public void setConnection(Connection connection) {
        this.connection = connectionManager.get();
        retrieveUserData();
    }

    @FXML
    private void exit() {
        System.exit(0);
    }

    private void addElementsWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/com/mirald/presentation/view/addNewElements.fxml"));
            loader.setControllerFactory(springContext::getBean);
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image(getClass()
                    .getResourceAsStream("/com/mirald/presentation/icon.png")));
            stage.setTitle("Adding items");

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteElementsWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/com/mirald/presentation/view/deleteElements.fxml"));
            loader.setControllerFactory(springContext::getBean);
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image(getClass()
                    .getResourceAsStream("/com/mirald/presentation/icon.png")));
            stage.setTitle("Delete items");

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addWindow() {
        addElementsWindow();
    }

    @FXML
    private void deleteWindow() {
        deleteElementsWindow();
    }

    private void openInfoWindow(String name, String details) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mirald/presentation/view/infos.fxml"));
            loader.setControllerFactory(springContext::getBean);
            Parent root = loader.load();

            InfoController infoController = loader.getController();
            infoController.setItemInfo(name, details);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/mirald/presentation/icon.png")));
            stage.setTitle("Information about the item");

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
