package az.hrportal.hrportalapi.error.exception;

public class EnumNotFoundException extends RuntimeException {
    public EnumNotFoundException(Class constant, Object enumValue) {
        super(constant.getSimpleName() + " enum is not found with ENUM : " + enumValue);
    }
}
