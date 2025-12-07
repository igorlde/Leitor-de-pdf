package ExceptionsLogs;

import java.io.FileNotFoundException;

public class NotFoundPathLogException extends FileNotFoundException {
    public NotFoundPathLogException(String message) {
        super(message);
    }
}
