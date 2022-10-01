package by.kovzov.uis.service.api;

import by.kovzov.uis.domain.dto.request.SignupRequest;
import by.kovzov.uis.domain.model.user.User;

public interface AuthService {

    User signup(SignupRequest signupRequest);
}
