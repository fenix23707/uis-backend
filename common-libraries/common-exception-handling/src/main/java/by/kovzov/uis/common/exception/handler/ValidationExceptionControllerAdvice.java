package by.kovzov.uis.common.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

import by.kovzov.uis.common.exception.dto.ExceptionResponseDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ValidationExceptionControllerAdvice {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ExceptionResponseDto> validationExceptionHandler(BindException e) {
        log.debug("Validation error", e);
        HttpStatus status = HttpStatus.BAD_REQUEST;

        List<String> validationMessages = e.getAllErrors().stream()
            .map(ObjectError::getDefaultMessage)
            .toList();

        ExceptionResponseDto response = ExceptionResponseDto.builder()
            .validationMessages(validationMessages)
            .message("Validation error.")
            .status(status.getReasonPhrase())
            .build();

        return new ResponseEntity<>(response, status);
    }
}
