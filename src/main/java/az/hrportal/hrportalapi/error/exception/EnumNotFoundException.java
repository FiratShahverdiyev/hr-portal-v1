package az.hrportal.hrportalapi.error.exception;

public class EnumNotFoundException extends RuntimeException {
    public EnumNotFoundException(Class constant, Integer enumValue) {
        super(constant.getSimpleName() + " enum is not found with ENUM : " + enumValue);
    }
}
