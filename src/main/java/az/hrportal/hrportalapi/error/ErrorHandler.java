package az.hrportal.hrportalapi.error;

import az.hrportal.hrportalapi.helper.i18n.LocaleMessageResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
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
        log.error("Api error, errorCode: {} message: {}", status, ex.getMessage());
        return ResponseEntity.status(400).body(new ErrorResponseDto(status.value(), message));
    }
}
