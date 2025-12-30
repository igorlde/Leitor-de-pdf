package ExceptionsLogs;

import java.nio.file.AccessDeniedException;
/**
 * @author igor
 */
public class AcessRejectedException extends AccessDeniedException {
    public AcessRejectedException(String message) {
        super(message);
    }
}
