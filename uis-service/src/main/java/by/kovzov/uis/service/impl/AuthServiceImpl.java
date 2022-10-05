package by.kovzov.uis.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

import by.kovzov.uis.domain.dto.request.SignupRequest;
import by.kovzov.uis.domain.model.user.User;
import by.kovzov.uis.domain.model.user.UserRole;
import by.kovzov.uis.domain.model.user.UserRoleName;
import by.kovzov.uis.domain.model.user.UserStatus;
import by.kovzov.uis.service.api.AuthService;
import by.kovzov.uis.service.api.UserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final Set<UserRole> DEFAULT_ROLES = Set.of(new UserRole(1L, UserRoleName.ADMIN)); //TODO use real default role

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User signup(SignupRequest signupRequest) {
        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setStatus(UserStatus.ACTIVE);
        user.setRoles(DEFAULT_ROLES);
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        userService.save(user);
        return user;
    }
}
