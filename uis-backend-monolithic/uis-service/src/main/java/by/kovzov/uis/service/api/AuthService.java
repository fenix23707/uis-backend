package by.kovzov.uis.service.api;

import by.kovzov.uis.domain.dto.auth.SignupDto;
import by.kovzov.uis.domain.entity.User;

public interface AuthService {

    User signup(SignupDto signupDto);
}
