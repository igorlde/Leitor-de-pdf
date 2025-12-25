package entity;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class PdfInfoClass {
    // this functions and to improve the velocity of cpu for load pdf
    private final Map<String, List<String>> cacheOfSearch = new LinkedHashMap<>(10, 0.75f,true){
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, List<String>> eldest) {
            return size() > 10;
        }
    };
    public PdfInfoClass(){}
    public String searchPagePdf(Integer search, Path pathOfPdf){
        String textPdf;
        try(PDDocument document = Loader.loadPDF(pathOfPdf.toFile())){//take care with it
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
    public List<String> searchContentPage(String search, Path pathOfPDf){
        if (cacheOfSearch.containsKey(search)) {
            System.out.println("Resultado vindo do cache...");
            return cacheOfSearch.get(search);
        }
        List<String> pageFinds = new ArrayList<>();
        try(PDDocument document = Loader.loadPDF(pathOfPDf.toFile())){
            PDFTextStripper stripper = new PDFTextStripper();
            int totalPages = document.getNumberOfPages();
            for(int i = 1;i <= totalPages;i++){
                    stripper.setStartPage(i);
                    stripper.setEndPage(i);
                    String textPage = stripper.getText(document);
                    if(textPage.toLowerCase().contains(search.toLowerCase())){
                        pageFinds.add(textPage);
                    }
            }
            cacheOfSearch.put(search, pageFinds);
        }catch(IOException e){
            throw new RuntimeException(e);
        }
        return pageFinds;
    }
//    public static void main(String[] args) {
////        String document = searchPagePdf(24, "/home/igor/Livros/The.C.Programming.Language.2Nd.Ed Prentice.Hall.Brian.W.Kernighan.and.Dennis.M.Ritchie..pdf");
//        List<Integer> pages = searchContentPage("int ", "/home/igor/Livros/The.C.Programming.Language.2Nd.Ed Prentice.Hall.Brian.W.Kernighan.and.Dennis.M.Ritchie..pdf");
////        System.out.println(document);
//        for (int i = 0;i < pages.size();i++){
//          System.out.println(pages);
//        }
//    }

}
