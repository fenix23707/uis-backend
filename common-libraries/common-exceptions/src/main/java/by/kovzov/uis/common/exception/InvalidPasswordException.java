package by.kovzov.uis.common.exception;

import java.util.List;

import org.springframework.http.HttpStatus;

public class InvalidPasswordException extends ServiceException {

    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

    public InvalidPasswordException(List<String> messages) {
        this(String.join("; ", messages));
    }

    public InvalidPasswordException(String message) {
        super(message, HTTP_STATUS);
    }

    public InvalidPasswordException(String message, Throwable cause) {
        super(message, cause, HTTP_STATUS);
    }
}
