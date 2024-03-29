package by.kovzov.uis.common.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ServiceException {

    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause, HttpStatus.NOT_FOUND);
    }
}
