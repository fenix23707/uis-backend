package by.kovzov.uis.security.service.api;

import by.kovzov.uis.security.domain.dto.SignupDto;
import by.kovzov.uis.security.repository.entity.User;

public interface AuthService {

    User signup(SignupDto signupDto);
}
