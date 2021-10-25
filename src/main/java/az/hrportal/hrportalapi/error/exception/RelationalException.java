package az.hrportal.hrportalapi.error.exception;

public class RelationalException extends RuntimeException {
    public RelationalException(Class entity) {
        super(entity.getSimpleName() + " ");
    }
}
