package az.hrportal.hrportalapi.error.exception;

public class DocumentException extends RuntimeException {
    public DocumentException(String message) {
        super(message);
    }

    public DocumentException(Exception exception) {
        super(exception);
    }
}
