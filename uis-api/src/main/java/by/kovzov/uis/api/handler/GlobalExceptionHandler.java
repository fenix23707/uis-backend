package by.kovzov.uis.api.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import by.kovzov.uis.domain.dto.response.ApiErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception e) {
        return buildResponseEntity(e, "Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<?> buildResponseEntity(Exception e, String reason, HttpStatus status) {
        ApiErrorResponse response = ApiErrorResponse.builder()
            .reason(reason)
            .status(status.getReasonPhrase())
            .description(e.getMessage())
            .build();
        return new ResponseEntity<>(response, status);
    }
}
