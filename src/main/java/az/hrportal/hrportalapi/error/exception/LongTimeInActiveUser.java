package az.hrportal.hrportalapi.error.exception;

public class LongTimeInActiveUser extends RuntimeException {
    public LongTimeInActiveUser(String message) {
        super(message);
    }
}
