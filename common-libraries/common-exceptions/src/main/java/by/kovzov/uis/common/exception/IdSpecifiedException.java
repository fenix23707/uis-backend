package by.kovzov.uis.common.exception;

import org.springframework.http.HttpStatus;

public class IdSpecifiedException extends ServiceException {

    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

    public IdSpecifiedException() {
        super("Id should not be specified for create endpoint", HTTP_STATUS);
    }
}
