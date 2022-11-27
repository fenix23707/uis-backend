package by.kovzov.uis.api.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import by.kovzov.uis.domain.dto.ApiErrorMessageDto;
import by.kovzov.uis.service.exception.ServiceException;

@RestControllerAdvice
public class ServiceExceptionAdvice {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<?> serviceExceptionHandler(ServiceException e) {
        return buildResponseEntity(e, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<?> buildResponseEntity(ServiceException e, HttpStatus status) {
        ApiErrorMessageDto response = ApiErrorMessageDto.builder()
            .reason(e.getReason())
            .status(status.getReasonPhrase())
            .description(e.getMessage())
            .build();
        return new ResponseEntity<>(response, status);
    }
}
