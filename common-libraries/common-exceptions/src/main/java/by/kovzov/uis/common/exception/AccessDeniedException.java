package by.kovzov.uis.common.exception;

import org.springframework.http.HttpStatus;

public class AccessDeniedException extends ServiceException{

    private static final HttpStatus HTTP_STATUS = HttpStatus.FORBIDDEN;


    public AccessDeniedException(String message) {
        super(message, HTTP_STATUS);
    }

    public AccessDeniedException(String message, Throwable cause) {
        super(message, cause, HTTP_STATUS);
    }
}
