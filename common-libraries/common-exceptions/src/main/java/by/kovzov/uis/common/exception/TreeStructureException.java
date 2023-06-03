package by.kovzov.uis.common.exception;

import org.springframework.http.HttpStatus;

public class TreeStructureException extends ServiceException{

    private static final HttpStatus HTTP_STATUS = HttpStatus.CONFLICT;

    public TreeStructureException(String message) {
        super(message, HTTP_STATUS);
    }

    public TreeStructureException(String message, Throwable cause) {
        super(message, cause, HTTP_STATUS);
    }
}
