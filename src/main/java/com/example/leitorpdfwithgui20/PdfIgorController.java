package com.example.leitorpdfwithgui20;

import entity.ReadPdfCore;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.awt.image.BufferedImage;
import javafx.scene.control.ScrollPane;
import java.io.File;
import java.nio.file.Path;

public class PdfIgorController {
    private static File auxValueFile;//this variable is for help get value of files
    private static int currentPage = 0;
    private static float zoom = 1.5f;


    private static boolean load = false;
    @FXML private VBox vBox;
    @FXML private TextField txtSearchPage;
    @FXML private ScrollPane scrollPane;

    @FXML
    public void initialize() {
        if (scrollPane != null) {
            scrollPane.vvalueProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal.doubleValue() >= 1.0 && !load) {
                    nextPage();
                }
                else if (newVal.doubleValue() <= 0.0 && currentPage > 0) {
                    previousPage();
                }
            });
        }
    }

    @FXML
    void zoomPage(KeyEvent event) {
        if (event.isControlDown()) {
            if (event.getCode() == KeyCode.PLUS || event.getCode() == KeyCode.ADD || event.getCode() == KeyCode.EQUALS) {
                zoom += 0.2f;
                atualizarVisualizacaoComZoom();
            } else if (event.getCode() == KeyCode.MINUS || event.getCode() == KeyCode.SUBTRACT) {
                zoom = Math.max(0.5f, zoom - 0.2f);
                atualizarVisualizacaoComZoom();
            }
        }
    }

    private void atualizarVisualizacaoComZoom() {
        vBox.getChildren().clear();
        showPage();
    }
    private void nextPage(){
        if(load)return;
        load = true;
        currentPage++;
        showPage();
        new Thread(() -> {
            try { Thread.sleep(500); } catch (InterruptedException e) {}
            load = false;
        }).start();
    }

    private void previousPage() {
        if(load)return;
        load = true;
        currentPage--;
        showPage();
        new Thread(() -> {
            try { Thread.sleep(500); } catch (InterruptedException e) {}
            load = false;
        }).start();
    }
    @FXML
    public void searchFile(MouseEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivos PDF", "*.pdf"));
        File selectedFile = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        if(selectedFile != null){
            Path pathPdf = selectedFile.toPath();
            auxValueFile = selectedFile;
            showPage();
        }
        return;
    }

    private void showPage() {
        if (auxValueFile == null) return;
        new Thread(() -> {
            try {
                ReadPdfCore readPdfCore = new ReadPdfCore();
                BufferedImage bf = readPdfCore.readSpecificPage(auxValueFile.toPath(), currentPage, zoom);

                if (bf != null) {
                    WritableImage fxImage = SwingFXUtils.toFXImage(bf, null);

                    // back to the UI Thread for avoid the erro Gdk-WARNING
                    javafx.application.Platform.runLater(() -> {
                        ImageView imageView = new ImageView(fxImage);
                        imageView.setPreserveRatio(true);

                        // Ajuste dinâmico de largura para evitar que fique pequeno
                        imageView.setFitWidth(vBox.getWidth() > 0 ? vBox.getWidth() : 800 * zoom);

                        vBox.getChildren().add(imageView);
                    });
                }
            } catch (Exception e) {
                throw new RuntimeException("erro ao carregar "+e);
            }
        }).start();
    }
    @FXML
    void searchPage(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                int pageToSearch = Integer.parseInt(txtSearchPage.getText());
                if (pageToSearch >= 0) {
                    currentPage = pageToSearch;
                    vBox.getChildren().clear();
                    if (scrollPane != null) {
                        scrollPane.setVvalue(0.0);
                    }
                    showPage();
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número de página válido.");
            }
        }



}
