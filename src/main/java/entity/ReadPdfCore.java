package entity;

import corePDfinter.corePDfInterface;
import logClasses.MainLogClass;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
public class ReadPdfCore implements corePDfInterface {

    @Override
    public String readPDF(String pathOfAchive, Integer numberOfPage){
        PDDocument document = null;
        try{
            Path pdfPath = Paths.get(pathOfAchive);
            document = Loader.loadPDF(pdfPath.toFile());
            int totalPages = document.getNumberOfPages();
            if (numberOfPage < 1 || numberOfPage > totalPages) {
                return "Erro: Página " + numberOfPage + " fora do intervalo [1 - " + totalPages + "]";
            }
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setStartPage(numberOfPage);
            stripper.setEndPage(numberOfPage);
            String textoDaPagina = stripper.getText(document);
            return textoDaPagina;

        } catch (IOException e) {
            System.err.println("Erro ao carregar ou processar o arquivo PDF: " + e.getMessage());
            e.printStackTrace();
            return "Erro ao carregar o PDF: " + e.getMessage();
        } finally {
            if (document != null) {
                try {
                    document.close();
                } catch (IOException e) {
                }
            }
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
    @Override
    public String mixLogOfBooks(String pathOfAchive, Integer numberOfPage, Float zoom) throws IOException {
        MainLogClass mainLogClass = new MainLogClass();
        //this part of code are gonna for mixed log with readPDF.

        String nameBook = searchNameBook(pathOfAchive);
//        mainLogClass.logNewBook(nameBook, numberOfPage, zoom);
        String text = readPDF(pathOfAchive, numberOfPage);
        return text;
    }
}