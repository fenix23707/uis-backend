package by.kovzov.uis.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.text.MessageFormat;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException(Long id) {
        super(MessageFormat.format("User with id = {0} not found.", id));
    }

    public UserNotFoundException(String username) {
        super(MessageFormat.format("User with username = {0} not found.", username));
    }
}
