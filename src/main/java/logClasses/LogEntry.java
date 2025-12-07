package logClasses;

public class LogEntry {
    private final String timestamp;
    private final String bookName;
    private final int page;
    private final String zoom;

    public LogEntry(String timestamp, String bookName, int page, String zoom) {
        this.timestamp = timestamp;
        this.bookName = bookName;
        this.page = page;
        this.zoom = zoom;
    }

    // Getters
    public String getTimestamp() { return timestamp; }
    public String getBookName() { return bookName; }
    public int getPage() { return page; }
    public String getZoom() { return zoom; }

    @Override
    public String toString() {
        return String.format(
                "[%s] Livro: %s,  Página: %d,  Zoom: %s%%", // toString format this log
                timestamp, bookName, page, zoom
        );
    }
}
