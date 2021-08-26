package az.hrportal.hrportalapi.error;

public enum ErrorCode {
    INTERNAL_SERVER(101, "error.server"),
    ENTITY_NOT_FOUND(300, "error.entity.not-found"),
    CONSTANT_NOT_FOUND(301, "error.constant.not-found"),
    FILE_NOT_ALLOWED_EXTENSION(302, "error.file.not-allowed-extension"),
    BIND_EXCEPTION(303, "error.bind"),
    BAD_CREDENTIALS(304, "error.bad-credentials"),
    VALIDATION_NOT_EMPTY(305, "error.validation.not-empty"),
    VALIDATION_RANGE(306, "error.validation.range"),
    SESSION_EXPIRED(307, "error.session.expired"),
    INCORRECT_DATE_FORMAT(308, "error.incorrect.date-format"),
    DOCUMENT_PROBLEM(309, "error.file.document-problem"),
    BAD_REQUEST(400, "error.bad-request");

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
