package com.example.leitorpdfwithgui20;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PdfIgorController {
    @FXML
    private Label welcomeText;

    @FXML
    public void SearchFile(){}
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
