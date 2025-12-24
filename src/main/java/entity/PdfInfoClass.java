package entity;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
    public static List<Integer> searchContentPage(String search, String pathOfPDf){
        Path pathPdf = Paths.get(pathOfPDf);
       // String textPageFinds;
        List<Integer> pageFinds = new ArrayList<>();
        try(PDDocument document = Loader.loadPDF(pathPdf.toFile())){
            PDFTextStripper stripper = new PDFTextStripper();
            for(int i = 0;i <= document.getNumberOfPages();i++){
                    stripper.setStartPage(i);
                    stripper.setEndPage(i);
                    String textPage = stripper.getText(document);
                    if(textPage.toLowerCase().contains(search.toLowerCase())){
                        pageFinds.add(i);//count total result
                        //textPageFinds = stripper.getText(document);
                        //System.out.println(textPageFinds);//here get every content taht have in the page
                    }
            }
        }catch(IOException e){
            throw new RuntimeException(e);
        }
        return pageFinds;
    }
    public static void main(String[] args) {
//        String document = searchPagePdf(24, "/home/igor/Livros/The.C.Programming.Language.2Nd.Ed Prentice.Hall.Brian.W.Kernighan.and.Dennis.M.Ritchie..pdf");
        List<Integer> pages = searchContentPage("int ", "/home/igor/Livros/The.C.Programming.Language.2Nd.Ed Prentice.Hall.Brian.W.Kernighan.and.Dennis.M.Ritchie..pdf");
//        System.out.println(document);
        for (int i = 0;i < pages.size();i++){
          System.out.println(pages);
        }
    }

}
