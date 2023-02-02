package by.kovzov.uis.common.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import by.kovzov.uis.common.exception.ServiceException;
import by.kovzov.uis.common.exception.dto.ExceptionResponseDto;

@RestControllerAdvice
public class ServiceExceptionAdvice {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<?> serviceExceptionHandler(ServiceException e) {
        return buildResponseEntity(e, e.getHttpStatus());
    }

    private ResponseEntity<?> buildResponseEntity(ServiceException e, HttpStatus status) {
        ExceptionResponseDto response = ExceptionResponseDto.builder()
            .message(e.getMessage())
            .status(status.getReasonPhrase())
            .build();
        return new ResponseEntity<>(response, status);
    }
}
