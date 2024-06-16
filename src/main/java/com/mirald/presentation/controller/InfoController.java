package com.mirald.presentation.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component
public class InfoController {

    @FXML
    private Label itemLabel;

    @FXML
    private Label detailsLabel;

    @FXML
    private Button exitButton;

    public void setItemInfo(String name, String details) {
        itemLabel.setText(name);
        detailsLabel.setText(details);
    }

    @FXML
    private void exit() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

}
