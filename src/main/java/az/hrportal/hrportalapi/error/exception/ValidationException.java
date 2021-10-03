package az.hrportal.hrportalapi.error.exception;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super("Validation exception : " + message);
    }
}
