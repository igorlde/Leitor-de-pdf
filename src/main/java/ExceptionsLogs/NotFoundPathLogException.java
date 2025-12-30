package ExceptionsLogs;

import java.io.FileNotFoundException;
/**
 * @author igor
 */
public class NotFoundPathLogException extends FileNotFoundException {
    public NotFoundPathLogException(String message) {
        super(message);
    }
}
