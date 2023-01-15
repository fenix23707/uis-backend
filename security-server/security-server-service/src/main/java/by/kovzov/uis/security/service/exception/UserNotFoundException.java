package by.kovzov.uis.security.service.exception;

import java.text.MessageFormat;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException(Long id) {
        super(MessageFormat.format("User with id = {0} not found.", id));
    }

    public UserNotFoundException(String username) {
        super(MessageFormat.format("User with username = {0} not found.", username));
    }
}
