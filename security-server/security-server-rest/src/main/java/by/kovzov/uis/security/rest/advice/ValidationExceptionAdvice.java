package by.kovzov.uis.security.rest.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

import by.kovzov.uis.security.domain.dto.error.ValidationErrorMessageDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ValidationExceptionAdvice {

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
}
