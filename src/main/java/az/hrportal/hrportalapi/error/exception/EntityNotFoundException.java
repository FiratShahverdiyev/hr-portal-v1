package az.hrportal.hrportalapi.error.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(Class entity, Object id) {
        super(entity.getSimpleName() + " is not found with ID: " + id);
    }
}
