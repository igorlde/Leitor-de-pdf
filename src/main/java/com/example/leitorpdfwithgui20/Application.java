package com.example.leitorpdfwithgui20;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("pdfGUiLeitor.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1920, 1200);
        stage.setScene(scene);
        stage.show();
    }
}
