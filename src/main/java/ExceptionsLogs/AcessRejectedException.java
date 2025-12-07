package ExceptionsLogs;

import java.nio.file.AccessDeniedException;

public class AcessRejectedException extends AccessDeniedException {
    public AcessRejectedException(String message) {
        super(message);
    }
}
