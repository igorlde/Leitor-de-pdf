package entity;

import com.example.leitorpdfwithgui20.Application;
import corePDfinter.corePDfInterface;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * @author igor
 */
public class ReadPdfCore extends Application implements corePDfInterface {

    /**
     *
     * @param pathOfAchive
     * @return List<String
     * this functions read every pdf, but convert in text
     */
    @Override
    public List<String> readPDF(Path pathOfAchive) {

        try (PDDocument document = Loader.loadPDF(pathOfAchive.toFile());) {
            List<String> result = new ArrayList<>();
            int totalPages = document.getNumberOfPages();
            String textoDaPagina;
            PDFTextStripper stripper = new PDFTextStripper();
            for (int i = 1; i <= totalPages; ++i) {
                stripper.setStartPage(i);
                stripper.setEndPage(i);
                textoDaPagina = stripper.getText(document);
                result.add(textoDaPagina);
            }
            return result;

        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar ou processar o arquivo PDF: " + e);
        }
    }

    /**
     *
     * @param pathOfPdf
     * @param pageIndex
     * @param zoom
     * @return BufferedImage
     * this functions read every pdf and reder, it images
     */
    public BufferedImage readSpecificPage(Path pathOfPdf, int pageIndex, float zoom) {
        try (PDDocument document = Loader.loadPDF(pathOfPdf.toFile())) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            if (pageIndex >= 0 && pageIndex < document.getNumberOfPages()) {
                return pdfRenderer.renderImage(pageIndex, 1.5f);
            }
        } catch (IOException | IllegalStateException | IndexOutOfBoundsException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     *
     * @param pathOfAchive
     * @return String
     * this function get of name from pdf and catch only te name of pdf
     * for pass to log.
     */
    public String searchNameBook(String pathOfAchive) {
        String partOfStringName = pathOfAchive.replace('\\', '/');
        String[] parts = partOfStringName.split("/");
        String nameBook = null;
        if (parts.length > 0) {
            nameBook = parts[parts.length - 1];
            int lastDotIndex = nameBook.lastIndexOf('.');
            if (lastDotIndex > 0) {
                return nameBook.substring(0, lastDotIndex);
            }
        }
        return null;
    }
}