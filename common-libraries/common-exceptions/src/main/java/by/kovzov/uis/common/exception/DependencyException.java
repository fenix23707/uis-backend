package by.kovzov.uis.common.exception;

import org.springframework.http.HttpStatus;

public class DependencyException extends ServiceException {

    private static final HttpStatus HTTP_STATUS = HttpStatus.CONFLICT;

    public DependencyException(String message) {
        super(message, HTTP_STATUS);
    }

    public DependencyException(String message, Throwable cause) {
        super(message, cause, HTTP_STATUS);
    }
}
