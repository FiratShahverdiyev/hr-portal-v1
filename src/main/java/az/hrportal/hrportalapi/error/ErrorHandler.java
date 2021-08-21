package az.hrportal.hrportalapi.error;

import az.hrportal.hrportalapi.error.exception.EntityNotFoundException;
import az.hrportal.hrportalapi.error.exception.EnumNotFoundException;
import az.hrportal.hrportalapi.error.exception.FileExtensionNotAllowedException;
import az.hrportal.hrportalapi.error.exception.InvalidTokenException;
import az.hrportal.hrportalapi.helper.i18n.LocaleMessageResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ErrorHandler extends ResponseEntityExceptionHandler {
    private final LocaleMessageResolver messageResolver;

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
                                                         WebRequest request) {
        ErrorCode errorCode = ErrorCode.BIND_EXCEPTION;
        String message = messageResolver.resolve(errorCode.getMessage());
        log.error("---------- Api error, errorCode: {} message: {} ----------", status, ex.getMessage());
        return ResponseEntity.status(400).body(new ErrorResponseDto(message, status.value()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> entityNotFound(final Exception e) {
        ErrorCode errorCode = ErrorCode.ENTITY_NOT_FOUND;
        String message = messageResolver.resolve(errorCode.getMessage());
        log.error("---------- Api error, errorCode: {} message: {} ----------", errorCode.getCode(), e.getMessage());
        return ResponseEntity.status(404).body(new ErrorResponseDto(message, errorCode.getCode()));
    }

    @ExceptionHandler(EnumNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> constantNotFound(final Exception e) {
        ErrorCode errorCode = ErrorCode.CONSTANT_NOT_FOUND;
        String message = messageResolver.resolve(errorCode.getMessage());
        log.error("---------- Api error, errorCode: {} message: {} ----------", errorCode.getCode(), e.getMessage());
        return ResponseEntity.status(400).body(new ErrorResponseDto(message, errorCode.getCode()));
    }

    @ExceptionHandler(FileExtensionNotAllowedException.class)
    public ResponseEntity<ErrorResponseDto> fileExtensionNotAllowed(final Exception e) {
        ErrorCode errorCode = ErrorCode.FILE_NOT_ALLOWED_EXTENSION;
        String message = messageResolver.resolve(errorCode.getMessage());
        log.error("---------- Api error, errorCode: {} message: {} ----------", errorCode.getCode(), e.getMessage());
        return ResponseEntity.status(400).body(new ErrorResponseDto(message, errorCode.getCode()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDto> badCredentials(final Exception e) {
        ErrorCode errorCode = ErrorCode.BAD_CREDENTIALS;
        String message = messageResolver.resolve(errorCode.getMessage());
        log.error("---------- Api error, errorCode: {} message: {} ----------", errorCode.getCode(), e.getMessage());
        return ResponseEntity.status(400).body(new ErrorResponseDto(message, errorCode.getCode()));
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorResponseDto> invalidToken(final Exception e) {
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER;
        String message = messageResolver.resolve(errorCode.getMessage());
        log.error("---------- Api error, errorCode: {} message: {} ----------", errorCode.getCode(), e.getMessage());
        return ResponseEntity.status(500).body(new ErrorResponseDto(message, errorCode.getCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> internalServerError(final Exception e) {
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER;
        String message = messageResolver.resolve(errorCode.getMessage());
        StringBuilder errorLog = new StringBuilder();
        errorLog.append("\n").append(e.getClass()).append(" : ").append(e.getMessage()).append("\n");
        for (StackTraceElement trace : e.getStackTrace()) {
            errorLog.append(trace).append("\n");
        }
        log.error("---------- Api error, errorCode: {} message: {} ----------", errorCode.getCode(), errorLog);
        return ResponseEntity.status(500).body(new ErrorResponseDto(message, errorCode.getCode()));
    }
}
