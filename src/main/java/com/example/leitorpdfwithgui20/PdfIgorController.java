package com.example.leitorpdfwithgui20;

import ExceptionsLogs.ConnotIvokePathsException;
import entity.PdfInfoClass;
import entity.ReadPdfCore;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class PdfIgorController {
    private static File auxValueFile;//this variable is for help get value of files

    @FXML
    private TextArea txtContent;

    @FXML
    private TextField txtSearchPage;

    @FXML
    public void searchFile(MouseEvent event){
        FileChooser fileChooser = new FileChooser();
        ReadPdfCore readPdfCore = new ReadPdfCore();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivos PDF", "*.pdf"));
        File selectedFile = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        String text = null;
        if(selectedFile != null){
            Path pathPdf = selectedFile.toPath();
            text = readPdfCore.readPDF(pathPdf).toString();
        }
        txtContent.setText(text);
        auxValueFile = selectedFile;
        return;
    }
    @FXML
    void searchPage(KeyEvent event) {
            String textKey = txtSearchPage.getCharacters().toString();
            String textSearch = null;
            PdfInfoClass pdfInfoClass = new PdfInfoClass();
            File selectedFile = auxValueFile;
            Path pathPdf = selectedFile.toPath();
            if(!Files.exists(pathPdf)){
                throw new ConnotIvokePathsException("provalvemente a variavel selectedFile esta null");
            }
               textSearch = pdfInfoClass.searchContentPage(textKey, pathPdf).toString();
            txtContent.setText(textSearch);
            return;
    }


}
