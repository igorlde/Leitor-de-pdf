package corePDfinter;
import java.nio.file.Path;
import java.util.List;
/**
 * @author igor
 * interface for implements in class
 * @see entity.ReadPdfCore
 */
public interface corePDfInterface {
    public List<String> readPDF(Path pathOfAchive);

}
