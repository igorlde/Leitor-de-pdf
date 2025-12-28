package logClasses;

import com.example.leitorpdfwithgui20.PdfIgorController;

public class LogEntry {
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

    // Getters
    public String getTimestamp() { return timestamp; }
    public String getBookName() { return bookName; }
    public int getPage() { return page; }
    public float getZoom() { return zoom; }

    public static boolean isFindLog(){
        PdfIgorController isFind = new PdfIgorController();
        return isFind.getFind();
    }
    @Override
    public String toString() {
        return String.format(
                "[%s] Livro: %s,  Página: %d,  Zoom: %s%%", // toString format this log
                timestamp, bookName, page, zoom
        );
    }
}
