package by.kovzov.uis.auth.service.exception;

import lombok.Getter;

@Getter
public abstract class ServiceException extends RuntimeException {

    private String reason;

    public ServiceException(String reason) {
        this.reason = reason;
    }

    public ServiceException(String reason, Throwable cause) {
        super(cause);
        this.reason = reason;
    }
}
