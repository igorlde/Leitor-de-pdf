package ExceptionsLogs;

import java.nio.file.NoSuchFileException;
/**
 * @author igor
 */
public class NotFoundNoSuchException extends NoSuchFileException {
    public NotFoundNoSuchException(String message) {
        super(message);
    }
}
