package by.kovzov.uis.common.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import by.kovzov.uis.common.exception.dto.ExceptionResponseDto;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> accessDeniedHandler(AccessDeniedException e) {
        return buildResponseEntity(e, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> authenticationExceptionHandler(AuthenticationException e) {
        return buildResponseEntity(e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception e) {
        log.error("Server error occurred", e);
        return buildResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<?> buildResponseEntity(Exception e, HttpStatus status) {
        ExceptionResponseDto response = ExceptionResponseDto.builder()
            .message(e.getMessage())
            .status(status.getReasonPhrase())
            .build();
        return new ResponseEntity<>(response, status);
    }
}
