package com.example.lms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    public void onButton1(DragEvent dragEvent) {
        welcomeText.setText("Tan");
    }


    public void onButton(DragEvent dragEvent) {
        welcomeText.setText("Tan");
    }
}