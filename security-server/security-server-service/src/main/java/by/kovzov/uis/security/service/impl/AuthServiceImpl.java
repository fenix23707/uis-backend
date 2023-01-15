package by.kovzov.uis.security.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import by.kovzov.uis.security.domain.dto.SignupDto;
import by.kovzov.uis.security.domain.entity.User;
import by.kovzov.uis.security.domain.entity.UserStatus;
import by.kovzov.uis.security.service.api.AuthService;
import by.kovzov.uis.security.service.api.UserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User signup(SignupDto signupDto) {
        User user = new User();
        user.setUsername(signupDto.getUsername());
        user.setStatus(UserStatus.ACTIVE);
        user.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        userService.create(user);
        return user;
    }
}
