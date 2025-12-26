package entity;

import com.example.leitorpdfwithgui20.Application;
import corePDfinter.corePDfInterface;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import javafx.scene.control.ScrollPane;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReadPdfCore extends Application implements corePDfInterface {

    @Override
    public List<String> readPDF(Path pathOfAchive){

        try(PDDocument document = Loader.loadPDF(pathOfAchive.toFile());){
            List<String> result = new ArrayList<>();
            int totalPages = document.getNumberOfPages();
            String textoDaPagina = null;
            PDFTextStripper stripper = new PDFTextStripper();
            for(int i = 1;i <= totalPages;++i) {
                stripper.setStartPage(i);
                stripper.setEndPage(i);
                 textoDaPagina = stripper.getText(document);
                 result.add(textoDaPagina);
            }
            return result;

        } catch (IOException e) {
           throw new RuntimeException("Erro ao carregar ou processar o arquivo PDF: "+e);
        }
    }

    public BufferedImage readSpecificPage(Path pathOfPdf, int pageIndex, float zoom) {
        try (PDDocument document = Loader.loadPDF(pathOfPdf.toFile())) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            if (pageIndex >= 0 && pageIndex < document.getNumberOfPages()) {
                return pdfRenderer.renderImage(pageIndex, 1.5f); // 1.5f é o zoom/qualidade
            }
        } catch (IOException | IllegalStateException | IndexOutOfBoundsException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public int getTotalPages(Path pathOfPdf) {
        try (PDDocument document = Loader.loadPDF(pathOfPdf.toFile())) {
            return document.getNumberOfPages();
        } catch (IOException e) {
            return 0;
        }
    }


    private String searchNameBook(String pathOfAchive){
        String partOfStringName = pathOfAchive.replace('\\', '/');
        String[] parts = partOfStringName.split("/");
        String nameBook = null;
        if(parts.length > 0){
            nameBook = parts[parts.length - 1];
            int lastDotIndex = nameBook.lastIndexOf('.');
            if (lastDotIndex > 0) {
                return nameBook.substring(0, lastDotIndex);
            }
        }
        return null;
    }
//    @Override
//    public void start(Stage stage) {
//        String pathStr = "/home/igor/Livros/The.C.Programming.Language.2Nd.Ed Prentice.Hall.Brian.W.Kernighan.and.Dennis.M.Ritchie..pdf";
//        Path path1 = Paths.get(pathStr);
//
//        ReadPdfCore readPdfCore = new ReadPdfCore();
//        List<BufferedImage> bfList = readPdfCore.readGrapficsPdf(path1);
//        if (bfList != null) {
//            VBox containerPaginas = new VBox(10); // Espaço de 10px entre páginas
//
//            for (BufferedImage bf : bfList) {
//                WritableImage fxImage = SwingFXUtils.toFXImage(bf, null);
//                ImageView pgn = new ImageView(fxImage);
//                pgn.setPreserveRatio(true);
//                pgn.setFitWidth(750);
//                containerPaginas.getChildren().add(pgn);
//            }
//
//            ScrollPane scroll = new ScrollPane(containerPaginas);
//            Scene scene = new Scene(scroll, 800, 600);
//            stage.setScene(scene);
//            stage.show();
//        }
//    }
//
//    // 3. O main apenas inicia o JavaFX
//    public static void main(String[] args) {
//        launch(args);
//    }
}