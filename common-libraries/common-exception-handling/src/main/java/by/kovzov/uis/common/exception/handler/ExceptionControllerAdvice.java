package by.kovzov.uis.common.exception.handler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import by.kovzov.uis.common.exception.dto.ExceptionResponseDto;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
@Order
public class ExceptionControllerAdvice {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionResponseDto> accessDeniedHandler(AccessDeniedException e) {
        return buildResponseEntity(e, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ExceptionResponseDto> authenticationExceptionHandler(AuthenticationException e) {
        return buildResponseEntity(e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDto> globalExceptionHandler(Exception e) {
        log.error("Server error occurred", e);
        return buildResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ExceptionResponseDto> buildResponseEntity(Exception e, HttpStatus status) {
        ExceptionResponseDto response = ExceptionResponseDto.builder()
            .message(e.getMessage())
            .status(status.getReasonPhrase())
            .build();
        return new ResponseEntity<>(response, status);
    }
}
