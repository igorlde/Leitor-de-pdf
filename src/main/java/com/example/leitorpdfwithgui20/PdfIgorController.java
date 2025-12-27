package com.example.leitorpdfwithgui20;

import ExceptionsLogs.ProcessInterruptedException;
import entity.ReadPdfCore;
import javafx.css.Match;
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
import logClasses.LogEntry;
import logClasses.MainLogClass;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;

public class PdfIgorController {
    //this variables here do log too.
    private static File auxValueFile;//this variable is for help get value of files
    private static int currentPage = 0;
    private static float zoom = 1.5f;
    private static String nameBook;

    //https://osprogramadores.com/blog/2023/03/09/introducao-profiler-java/, i take of this site
    private static final BufferedWriter WRITER = new BufferedWriter(new OutputStreamWriter(System.out));

    private static final Pattern pattern = Pattern.compile("^\\[(.*?)\\] Livro: (.*?), Página: (\\d+), Zoom: (.*?)%$");
    private static boolean load = false;
    @FXML
    private VBox vBox;
    @FXML
    private TextField txtSearchPage;
    @FXML
    private ScrollPane scrollPane;

    @FXML
    public void initialize() {
        if (scrollPane != null) {
            scrollPane.vvalueProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal.doubleValue() >= 1.0 && !load) {
                    nextPage();
                } else if (newVal.doubleValue() <= 0.0 && currentPage > 0) {
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
                updateViewWithZoom();
            } else if (event.getCode() == KeyCode.MINUS || event.getCode() == KeyCode.SUBTRACT) {
                zoom = Math.max(0.5f, zoom - 0.2f);
                updateViewWithZoom();
            }
        }
    }

    private void updateViewWithZoom() {
        vBox.getChildren().clear();
        showPage();
    }

    private void nextPage() {
        if (load) return;
        load = true;
        currentPage++;
        showPage();
        new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new ProcessInterruptedException("erro ao pula para a proxima pagina."+ e);
            }
            load = false;
        }).start();//fix this functions after
    }

    private void previousPage() {
        if (load) return;
        load = true;
        currentPage--;
        showPage();
        new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new ProcessInterruptedException("erro ao pula para a proxima pagina."+ e);
            }
            load = false;
        }).start();//fix this functions after
    }

    @FXML
    public void searchFile(MouseEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        ReadPdfCore readPdfCore = new ReadPdfCore();
        MainLogClass mainLogClass = new MainLogClass();
        List<LogEntry> listLog = mainLogClass.sendBackInformationLog();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivos PDF", "*.pdf"));
        File selectedFile = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        for (LogEntry entry : listLog) {
            nameBook = entry.getBookName();
            if (readPdfCore.searchNameBook(selectedFile.toString().toLowerCase()).equalsIgnoreCase(nameBook)) {
                currentPage = entry.getPage();
                zoom = entry.getZoom();
            }
        }

        if (selectedFile != null) {
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
                        informationsLog();
                    });
                }
            } catch (Exception e){
                throw new RuntimeException("Erro a carregar pdf"+e);
            }
        }).start();
    }

    @FXML
    void searchPage(KeyEvent event) throws IOException {
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
                WRITER.write("Por favor, digite um número de página válido.");
                WRITER.flush();
                WRITER.close();
            }
        }
    }

    private void informationsLog() {
        ReadPdfCore readPdfCore = new ReadPdfCore();
        MainLogClass mainLogClass;
        try {
            mainLogClass = new MainLogClass();
            nameBook = readPdfCore.searchNameBook(auxValueFile.toString());
            mainLogClass.createdLog(nameBook, currentPage, zoom);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
