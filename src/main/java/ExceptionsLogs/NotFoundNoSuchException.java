package ExceptionsLogs;

import java.nio.file.NoSuchFileException;

public class NotFoundNoSuchException extends NoSuchFileException {
    public NotFoundNoSuchException(String message) {
        super(message);
    }
}
