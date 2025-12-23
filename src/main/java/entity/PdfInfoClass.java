package entity;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PdfInfoClass {

    public static String searchPagePdf(Integer search, String pathOfPdf){
        Path pathPdf = Paths.get(pathOfPdf);
        String textPdf;
        try(PDDocument document = Loader.loadPDF(pathPdf.toFile())){//take care with it
         if(search < 1 || search > document.getNumberOfPages()) {
             return null;
          }
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setStartPage(search);
            stripper.setEndPage(search);
             textPdf = stripper.getText(document);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return textPdf;
    }
    //create a function for search for a String type a search chapter for name

    public static void main(String[] args) {
        String document = searchPagePdf(1, "/home/igor/Desktop/testPDF.pdf");
        System.out.println(document);
    }

}
