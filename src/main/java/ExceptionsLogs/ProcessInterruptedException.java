package ExceptionsLogs;
/**
 * @author igor
 */
public class ProcessInterruptedException extends RuntimeException {
    public ProcessInterruptedException(String message) {
        super(message);
    }
}
