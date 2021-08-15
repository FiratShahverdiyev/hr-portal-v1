package az.hrportal.hrportalapi.error;

public enum ErrorCode {
    INTERNAL_SERVER(101, "error.server"),
    ENTITY_NOT_FOUND(300, "error.not-found"),
    BIND_EXCEPTION(303, "error.bind"),
    BAD_REQUEST(400, "error.server");

    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
