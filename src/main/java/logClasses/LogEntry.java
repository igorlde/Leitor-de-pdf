package logClasses;

import com.example.leitorpdfwithgui20.PdfIgorController;

/**
 * @author igor
 */
public class LogEntry {
    /**
     * {@code String timestamp;}
     * {@code String bookName;}
     * {@code int page;}
     * {@code float zooms;}
     */
    private final String timestamp;
    private final String bookName;
    private final int page;
    private final float zoom;


    public LogEntry(String timestamp, String bookName, int page, float zoom) {
        this.timestamp = timestamp;
        this.bookName = bookName;
        this.page = page;
        this.zoom = zoom;
    }

    /**
     *
     * @return String
     */
    public String getTimestamp() { return timestamp; }
    /**
     *
     * @return String
     */
    public String getBookName() { return bookName; }
    /**
     *
     * @return int
     */
    public int getPage() { return page; }
    /**
     *
     * @return float
     */
    public float getZoom() { return zoom; }

    public static boolean isFindLog(){
        PdfIgorController isFind = new PdfIgorController();
        return isFind.getFind();
    }
    /**
     *
     * @return String
     */
    @Override
    public String toString() {
        return String.format(
                "[%s] Livro: %s,  Página: %d,  Zoom: %s%%", // toString format this log
                timestamp, bookName, page, zoom
        );
    }
}
