package by.kovzov.uis.security.service.api;

import by.kovzov.uis.security.domain.dto.SignupDto;
import by.kovzov.uis.security.domain.entity.User;

public interface securityService {

    User signup(SignupDto signupDto);
}
