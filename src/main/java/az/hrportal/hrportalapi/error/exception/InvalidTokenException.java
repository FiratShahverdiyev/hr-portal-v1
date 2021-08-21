package az.hrportal.hrportalapi.error.exception;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String token) {
        super("Token is not valid. TOKEN : " + token);
    }
}
