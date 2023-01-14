package by.kovzov.uis.auth.service.api;

import by.kovzov.uis.auth.domain.dto.SignupDto;
import by.kovzov.uis.auth.domain.entity.User;

public interface AuthService {

    User signup(SignupDto signupDto);
}
