package az.hrportal.hrportalapi.error.exception;

public class EmployeeNotActiveException extends RuntimeException {
    public EmployeeNotActiveException(String message) {
        super(message);
    }
}
