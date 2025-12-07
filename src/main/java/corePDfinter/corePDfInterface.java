package corePDfinter;

import java.io.IOException;

public interface corePDfInterface {
    public String readPDF(String pathOfAchive, Integer numberOfPage);
    public String mixLogOfBooks(String pathOfAchive, Integer numberOfPage, Float zoom) throws IOException;

}
