package by.kovzov.uis.api.advice;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import by.kovzov.uis.domain.dto.ApiErrorMessageDto;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception e) {
        log.error("Server error occurred", e);
        return buildResponseEntity(e, "Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<?> buildResponseEntity(Exception e, String reason, HttpStatus status) {
        ApiErrorMessageDto response = ApiErrorMessageDto.builder()
            .reason(reason)
            .status(status.getReasonPhrase())
            .description(e.getMessage())
            .build();
        return new ResponseEntity<>(response, status);
    }
}
