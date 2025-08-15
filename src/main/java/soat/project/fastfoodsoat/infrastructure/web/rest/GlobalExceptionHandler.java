package soat.project.fastfoodsoat.infrastructure.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import soat.project.fastfoodsoat.domain.validation.DomainError;
import soat.project.fastfoodsoat.infrastructure.web.model.DefaultApiError;
import soat.project.fastfoodsoat.domain.exception.DomainException;
import soat.project.fastfoodsoat.domain.exception.NotFoundException;
import soat.project.fastfoodsoat.shared.utils.InstantUtils;

import java.util.List;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<DefaultApiError> handleNotFoundException(final NotFoundException ex) {
        log.error("NotFoundException: ", ex);
        final var error = new DefaultApiError(
                InstantUtils.now(),
                HttpStatus.NOT_FOUND.value(),
                ex.getErrors()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(value = DomainException.class)
    public ResponseEntity<DefaultApiError> handleDomainException(final DomainException ex) {
        log.error("DomainException: ", ex);
        final var error = new DefaultApiError(
                InstantUtils.now(),
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                ex.getErrors()
        );
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<DefaultApiError> handleException(final Exception ex) {
        log.error("Exception: ", ex);
        final var error = new DefaultApiError(
                InstantUtils.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                List.of()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<DefaultApiError> handleRuntimeException(final RuntimeException ex) {
        log.error("RunTimeException: ", ex);
        final var error = new DefaultApiError(
                InstantUtils.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                List.of()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DefaultApiError> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("DomainException: ", ex);
        List<DomainError> errorsList = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new DomainError(
                        fieldError.getDefaultMessage()
                )).toList();

        final var error = new DefaultApiError(
                InstantUtils.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                errorsList
        );

        return  ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(error);
    }
}
