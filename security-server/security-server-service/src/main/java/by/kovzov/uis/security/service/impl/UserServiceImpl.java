package by.kovzov.uis.security.service.impl;

import static java.text.MessageFormat.format;

import org.springframework.stereotype.Service;

import by.kovzov.uis.common.exception.NotFoundException;
import by.kovzov.uis.security.repository.api.UserRepository;
import by.kovzov.uis.security.repository.entity.User;
import by.kovzov.uis.security.service.api.UserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getById(String id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new NotFoundException(format("User with username = {0} not found.", username)));
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }
}
