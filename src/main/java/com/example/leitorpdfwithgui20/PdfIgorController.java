package com.example.leitorpdfwithgui20;

import ExceptionsLogs.ProcessInterruptedException;
import OSEnums.DateRemoveLogEnum;
import entity.ReadPdfCore;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
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
import java.util.List;

/**
 * @author igor
 *
 * this class is the main controller
 *
 */
public class PdfIgorController {
    /**
    These variables here are also used for logging.
     To see the log and how it works, go into the
     LogClasses package and examine the {@link MainLogClass}

     {@code File auxValueFIle} it variable is for get value of the files
     from method searchFile to help pass this values for other functions.

     {@code int currentPage} it variables is for get page of pdf.

     {@code float zoom} it simple, get values for zoom.
      for a cal of zoom get value zoom * 100, if zoom = 1.1, zoom = 1.1 * 100 = 110% of zoom
     {@code String nameBook} it get the name of pdf
     **/

    private static File auxValueFile;
    private static int currentPage;
    private static float zoom;
    private static String nameBook;
    private static  int page = 0;
    private static boolean isThreePage = false;

    /**
     * https://osprogramadores.com/blog/2023/03/09/introducao-profiler-java/, i take of this site
     {@code BufferedWriter WRITER }This variable provides an improvement to the system,
     because strings in Java consume a lot of memory if they are not controlled.

     {@code boolean load}this variable is a
     change of check, if will load the next page or previous page

     {@code boolena isFind}This variable is a way to
     check if it also finds the PDF log.
     This is to avoid creating a very large O(n) loop.
     {@link MainLogClass} {@link LogEntry} <- it variable and
     use here as well.
    */
    private static final BufferedWriter WRITER = new BufferedWriter(new OutputStreamWriter(System.out));
    private static boolean load = false;
    private static boolean isFind;

    /**
     * {@code vBOx vBox} a new box for pages render.
     * {@code TextField txtSearchPage} variable that
     * get search per number
     * {@code ScrollPane scroolPane}
     */
    @FXML
    private VBox vBox;
    @FXML
    private TextField txtSearchPage;
    @FXML
    private ScrollPane scrollPane;

    /**
     * @return void
     */
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
        setZoom();
    }

    /**
            I created this function to place inside the `initialize`
            method because the `zoomPage` function is not working. then now
            zoom work.
            @return void
         */
    private void setZoom(){

        Platform.runLater(() -> {
            Scene scene = scrollPane.getScene();
            if (scene != null) {
                scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
                    if (event.isControlDown()) {
                        if (event.getCode() == KeyCode.PLUS || event.getCode() == KeyCode.ADD || event.getCode() == KeyCode.EQUALS) {
                            zoom += 0.2f;
                            updateViewWithZoom();
                            event.consume();
                        } else if (event.getCode() == KeyCode.MINUS || event.getCode() == KeyCode.SUBTRACT) {
                            zoom = Math.max(0.5f, zoom - 0.2f);
                            updateViewWithZoom();
                            event.consume();
                        }
                    }
                });
            }
        });
    }

    /**
     * this function clear the VBOX to a new page with reder diferent.
     */
    private void updateViewWithZoom() {
        vBox.getChildren().clear();
          System.out.println(zoom);// variable zoom and methods no called I'm gonna fix it.
        showPage();
    }

    /**
     * this function get for reder the next page
     * and as well, save page for log
     */
    private void nextPage() {
        if (load) return;
        load = true;
        page++;
        currentPage++;
        if(page >= 3){
            isThreePage = true;
            page = 0;
        }
        showPage();
        new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new ProcessInterruptedException("erro ao pula para a proxima pagina."+ e);
            }
            load = false;
        }).start();
    }

    /**
     * the function get previous pages
     * when you on scroll to up.
     */
    private void previousPage() {
        if (load) return;
        load = true;
        page--;
        currentPage--;
        if(page <= 3){
            isThreePage = true;
        }
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

    /**
     * this function get path of pdf.
     * and as well pass values of log for the variables
     * @param event
     * @throws IOException
     */
    @FXML
    public void searchFile(MouseEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivos PDF", "*.pdf"));
        File selectedFile = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());

        if(selectedFile == null)return;
        ReadPdfCore readPdfCore = new ReadPdfCore();
        String searchedName = readPdfCore.searchNameBook(selectedFile.getAbsolutePath().toLowerCase());
        MainLogClass mainLogClass = new MainLogClass();

        List<LogEntry> listLog = mainLogClass.sendBackInformationLog();
        /**this loop retrieves the
        last item from the list of logs and
         then uses that item to find the last recorded log.
         */
            for(int i = 1;i <= listLog.size();i++){
                LogEntry entry = listLog.get(i);
                if(entry.getBookName().equalsIgnoreCase(searchedName)){
                    nameBook = entry.getBookName();
                    currentPage = entry.getPage();
                    zoom = entry.getZoom();
                    isFind = true;
                    break;
                }
            }
            auxValueFile = selectedFile;
            showPage();
        }

    /**
     *this show page kk
     * and stuck every function in there
     */
    private void showPage() {
        if (auxValueFile == null) return;
        new Thread(() -> {
            try {
                ReadPdfCore readPdfCore = new ReadPdfCore();
                BufferedImage bf = readPdfCore.readSpecificPage(auxValueFile.toPath(), currentPage, zoom);
                if (bf != null) {
                    WritableImage fxImage = SwingFXUtils.toFXImage(bf, null);
                    // back to the UI Thread for avoid the erro Gdk-WARNING
                    Platform.runLater(() -> {
                        ImageView imageView = new ImageView(fxImage);
                        imageView.setPreserveRatio(true);

                        /*
                        have a math for zoom here where be (vbox.getWitdth() * zoom) -1
                         */
                        imageView.setFitWidth(vBox.getWidth() > 0 ? (vBox.getWidth() * zoom) - 2: (929 * zoom) - 2);

                        vBox.getChildren().add(imageView);
                        if(isThreePage)informationsLog();
                    });
                }
            } catch (Exception e){
                throw new RuntimeException("Erro a carregar pdf"+e);
            }
        }).start();
    }

    /**
     * this function pass value of page search for variables
     * @param event
     * @throws IOException
     */
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

    /**
     * this function get the values of variables and pass for is function
     * @see MainLogClass#createdLog(String, Integer, Float)
     * @see DateRemoveLogEnum#ONE_YEAR_REMOVE_LOG, this enum remove log when pass a year.
     *
     * and if you arrived here, happy new year advance, lest go 2026!!!
     */
    private void informationsLog() {
        ReadPdfCore readPdfCore = new ReadPdfCore();
        MainLogClass mainLogClass;
        try {
            mainLogClass = new MainLogClass();
            nameBook = readPdfCore.searchNameBook(auxValueFile.toString());
                mainLogClass.createdLog(nameBook, currentPage, zoom);
            DateRemoveLogEnum.ONE_YEAR_REMOVE_LOG.executeRemove();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * get variable find for pass to log.
     * @return boolean
     */
    public boolean getFind(){
        return isFind;
    }


}
