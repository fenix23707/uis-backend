package by.kovzov.uis.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import by.kovzov.uis.domain.dto.request.SignupRequest;
import by.kovzov.uis.domain.entity.User;
import by.kovzov.uis.domain.entity.UserStatus;
import by.kovzov.uis.service.api.AuthService;
import by.kovzov.uis.service.api.UserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User signup(SignupRequest signupRequest) {
        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setStatus(UserStatus.ACTIVE);
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        userService.create(user);
        return user;
    }
}
