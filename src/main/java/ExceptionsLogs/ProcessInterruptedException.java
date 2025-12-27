package ExceptionsLogs;

public class ProcessInterruptedException extends RuntimeException {
    public ProcessInterruptedException(String message) {
        super(message);
    }
}
