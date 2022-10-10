package by.kovzov.uis.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundException extends ServiceException {

    public NotFoundException(String reason) {
        super(reason);
    }

    public NotFoundException(String reason, Throwable cause) {
        super(reason, cause);
    }
}
