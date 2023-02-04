package by.kovzov.uis.common.exception.handler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import by.kovzov.uis.common.exception.ServiceException;
import by.kovzov.uis.common.exception.dto.ExceptionResponseDto;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ServiceExceptionControllerAdvice {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ExceptionResponseDto> serviceExceptionHandler(ServiceException e) {
        HttpStatus status = e.getHttpStatus();
        ExceptionResponseDto response = ExceptionResponseDto.builder()
            .message(e.getMessage())
            .status(status.getReasonPhrase())
            .build();
        return new ResponseEntity<>(response, status);
    }
}
