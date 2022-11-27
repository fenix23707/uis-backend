package by.kovzov.uis.service.exception;

public class NotFoundException extends ServiceException {

    public NotFoundException(String reason) {
        super(reason);
    }

    public NotFoundException(String reason, Throwable cause) {
        super(reason, cause);
    }
}
