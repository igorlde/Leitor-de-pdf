package entity;

import corePDfinter.corePDfInterface;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ReadPdfCore implements corePDfInterface {

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
}