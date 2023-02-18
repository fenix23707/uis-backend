package by.kovzov.uis.common.exception;

import org.springframework.http.HttpStatus;

public class AlreadyExistsException extends ServiceException{

    private static final HttpStatus HTTP_STATUS = HttpStatus.CONFLICT;

    public AlreadyExistsException(String message) {
        super(message, HTTP_STATUS);
    }

    public AlreadyExistsException(String message, Throwable cause) {
        super(message, cause, HTTP_STATUS);
    }
}
