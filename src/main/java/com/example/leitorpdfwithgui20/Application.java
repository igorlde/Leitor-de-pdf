package com.example.leitorpdfwithgui20;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        // O caminho deve ser exatamente o que o comando 'jar tf' retornou, com uma '/' na frente
        String fxmlPath = "/com/example/leitorpdfwithgui20/pdfGUiLeitor.fxml";

        var resource = getClass().getResource(fxmlPath);

        if (resource == null) {
            System.err.println("ERRO: O arquivo não foi encontrado no caminho: " + fxmlPath);
            return;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
        stage.setScene(scene);
        stage.setTitle("Leitor PDF Igor");
        stage.show();
    }
}
