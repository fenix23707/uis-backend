package by.kovzov.uis.security.rest.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

import by.kovzov.uis.security.domain.dto.error.ApiErrorMessageDto;
import by.kovzov.uis.security.domain.dto.error.ValidationErrorMessageDto;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionAdvice {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> accessDeniedHandler(AccessDeniedException e) {
        return buildResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> authenticationExceptionHandler(AuthenticationException e) {
        return buildResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> validationExceptionHandler(BindException e) {
        log.debug("Validation error", e);

        HttpStatus status = HttpStatus.BAD_REQUEST;
        List<String> validationMessages = e.getAllErrors().stream()
            .map(ObjectError::getDefaultMessage)
            .toList();
        ValidationErrorMessageDto response = ValidationErrorMessageDto.builder()
            .validationMessages(validationMessages)
            .reason("Validation error.")
            .status(status.getReasonPhrase())
            .build();

        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception e) {
        log.error("Server error occurred", e);
        return buildResponseEntity("Something went wrong", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<?> buildResponseEntity(String reason, HttpStatus status) {
        return buildResponseEntity(reason, null, status);
    }

    private ResponseEntity<?> buildResponseEntity(String reason, String description, HttpStatus status) {
        ApiErrorMessageDto response = ApiErrorMessageDto.builder()
            .reason(reason)
            .status(status.getReasonPhrase())
            .description(description)
            .build();
        return new ResponseEntity<>(response, status);
    }
}
