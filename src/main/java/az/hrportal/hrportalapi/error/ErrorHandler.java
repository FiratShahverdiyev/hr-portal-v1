package az.hrportal.hrportalapi.error;

import az.hrportal.hrportalapi.constant.DocumentType;
import az.hrportal.hrportalapi.constant.employee.BloodGroup;
import az.hrportal.hrportalapi.constant.employee.DriverCategory;
import az.hrportal.hrportalapi.constant.employee.FamilyCondition;
import az.hrportal.hrportalapi.constant.employee.Gender;
import az.hrportal.hrportalapi.constant.employee.MilitaryAchievement;
import az.hrportal.hrportalapi.constant.employee.RelationType;
import az.hrportal.hrportalapi.constant.employee.Series;
import az.hrportal.hrportalapi.constant.position.EducationDegree;
import az.hrportal.hrportalapi.constant.position.GenderDemand;
import az.hrportal.hrportalapi.constant.position.Level;
import az.hrportal.hrportalapi.constant.position.RequireFile;
import az.hrportal.hrportalapi.constant.position.SubWorkCalculateDegree;
import az.hrportal.hrportalapi.constant.position.VacancyCategory;
import az.hrportal.hrportalapi.constant.position.WorkCondition;
import az.hrportal.hrportalapi.constant.position.WorkMode;
import az.hrportal.hrportalapi.constant.position.WorkPlace;
import az.hrportal.hrportalapi.error.exception.*;
import az.hrportal.hrportalapi.helper.i18n.LocaleMessageResolver;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static az.hrportal.hrportalapi.error.ErrorHandlerUtil.getStackTrace;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ErrorHandler extends ResponseEntityExceptionHandler {
    private final LocaleMessageResolver messageResolver;

    @SneakyThrows
    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
                                                         WebRequest request) {
        ErrorCode errorCode = ErrorCode.BIND_EXCEPTION;
        String message = messageResolver.resolve(errorCode.getMessage());
        log.error("---------- Api error, errorCode: {} message: {} ---------- \n StackTrace : {}",
                status, ex.getMessage(), getStackTrace(ex));
        return ResponseEntity.status(400).body(new ErrorResponseDto(message, status.value()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        var ref = new Object() {
            String fieldName = null;
            String message = null;
        };
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            ref.fieldName = ((FieldError) error).getField();
            ref.message = error.getDefaultMessage();
            errors.put(ref.fieldName, ref.message);
        });
        errors.forEach((fieldName, message) -> {
            log.error("---------- Api error, errorCode: {} message: {} ----------",
                    400, fieldName + " - " + message);
        });
        return ResponseEntity.status(400)
                .body(new ErrorResponseDto(errors, 400));
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

    @ExceptionHandler({InvalidTokenException.class, LongTimeInActiveUser.class})
    public ResponseEntity<ErrorResponseDto> invalidToken(final Exception e) {
        ErrorCode errorCode = ErrorCode.TOKEN_INVALID;
        String message = messageResolver.resolve(errorCode.getMessage());
        log.error("---------- Api error, errorCode: {} message: {} ----------", errorCode.getCode(), e.getMessage());
        return ResponseEntity.status(401).body(new ErrorResponseDto(message, errorCode.getCode()));
    }

    @ExceptionHandler(DocumentException.class)
    public ResponseEntity<ErrorResponseDto> documentException(final Exception e) {
        ErrorCode errorCode = ErrorCode.DOCUMENT_PROBLEM;
        String message = messageResolver.resolve(errorCode.getMessage());
        log.error("---------- Api error, errorCode: {} message: {} ---------- \n StackTrace : {}",
                errorCode.getCode(), e.getMessage(), getStackTrace(e));
        return ResponseEntity.status(500).body(new ErrorResponseDto(message, errorCode.getCode()));
    }

    @ExceptionHandler(EmployeeNotActiveException.class)
    public ResponseEntity<ErrorResponseDto> employeeIsNotActive(final Exception e) {
        ErrorCode errorCode = ErrorCode.EMPLOYEE_NOT_ACTIVE;
        String message = messageResolver.resolve(errorCode.getMessage());
        log.error("---------- Api error, errorCode: {} message: {} ---------- \n StackTrace : {}",
                errorCode.getCode(), e.getMessage(), getStackTrace(e));
        return ResponseEntity.status(500).body(new ErrorResponseDto(message, errorCode.getCode()));
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ErrorResponseDto> dateTimeParse(final Exception e) {
        ErrorCode errorCode = ErrorCode.INCORRECT_DATE_FORMAT;
        String message = messageResolver.resolve(errorCode.getMessage());
        log.error("---------- Api error, errorCode: {} message: {} ---------- \n StackTrace : {}",
                errorCode.getCode(), e.getMessage(), getStackTrace(e));
        return ResponseEntity.status(400).body(new ErrorResponseDto(message, errorCode.getCode()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> illegalArgument(final Exception e) {
        ErrorCode errorCode = ErrorCode.BAD_REQUEST;
        String message = messageResolver.resolve(errorCode.getMessage());
        String incorrectEnum = getIncorrectEnum(e.getMessage());
        message = message.concat(" - ").concat(incorrectEnum)
                .concat(" : ").concat(getEnumPossibleValues(incorrectEnum));
        log.error("---------- Api error, errorCode: {} message: {} ---------- \n StackTrace : {}",
                errorCode.getCode(), e.getMessage(), getStackTrace(e));
        return ResponseEntity.status(400).body(new ErrorResponseDto(message, errorCode.getCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> internalServerError(final Exception e) {
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER;
        String message = messageResolver.resolve(errorCode.getMessage());
        log.error("---------- Api error, errorCode: {} message: {} ---------- \n StackTrace : {}",
                errorCode.getCode(), "INTERNAL SERVER", getStackTrace(e));
        return ResponseEntity.status(500).body(new ErrorResponseDto(message, errorCode.getCode()));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponseDto> validationException(final Exception e) {
        ErrorCode errorCode = ErrorCode.VALIDATION_NOT_EMPTY;
        String message = messageResolver.resolve(errorCode.getMessage());
        log.error("---------- Api error, errorCode: {} message: {}  must not be null ---------- \n StackTrace : {}",
                errorCode.getCode(), e.getMessage(), getStackTrace(e));
        return ResponseEntity.status(400).body(new ErrorResponseDto(e.getMessage() + " " + message,
                errorCode.getCode()));
    }

    @ExceptionHandler(RelationalException.class)
    public ResponseEntity<ErrorResponseDto> relationalException(final Exception e) {
        ErrorCode errorCode = ErrorCode.RELATIONAL_EXCEPTION;
        String message = messageResolver.resolve(errorCode.getMessage());
        log.error("---------- Api error, errorCode: {} message: {} can not be deleted ---------- \n StackTrace : {}",
                errorCode.getCode(), e.getMessage(), getStackTrace(e));
        return ResponseEntity.status(312).body(new ErrorResponseDto(e.getMessage() + " " + message,
                errorCode.getCode()));
    }

    private String getIncorrectEnum(String errorMessage) {
        String[] arr = errorMessage.split("\\.");
        if (arr.length >= 2)
            return arr[arr.length - 2];
        else
            return errorMessage;
    }

    private String getEnumPossibleValues(String incorrectEnum) {
        switch (incorrectEnum) {
            case "BloodGroup":
                return Arrays.toString(BloodGroup.values());
            case "DriverCategory":
                return Arrays.toString(DriverCategory.values());
            case "FamilyCondition":
                return Arrays.toString(FamilyCondition.values());
            case "Gender":
                return Arrays.toString(Gender.values());
            case "MilitaryAchievement":
                return Arrays.toString(MilitaryAchievement.values());
            case "RelationType":
                return Arrays.toString(RelationType.values());
            case "Series":
                return Arrays.toString(Series.values());
            case "EducationDegree":
                return Arrays.toString(EducationDegree.values());
            case "GenderDemand":
                return Arrays.toString(GenderDemand.values());
            case "Level":
                return Arrays.toString(Level.values());
            case "RequireFile":
                return Arrays.toString(RequireFile.values());
            case "SubWorkCalculateDegree":
                return Arrays.toString(SubWorkCalculateDegree.values());
            case "VacancyCategory":
                return Arrays.toString(VacancyCategory.values());
            case "WorkCondition":
                return Arrays.toString(WorkCondition.values());
            case "WorkMode":
                return Arrays.toString(WorkMode.values());
            case "WorkPlace":
                return Arrays.toString(WorkPlace.values());
            case "DocumentType":
                return Arrays.toString(DocumentType.values());
            default:
                return "Unhandled Enum!";
        }
    }
}
