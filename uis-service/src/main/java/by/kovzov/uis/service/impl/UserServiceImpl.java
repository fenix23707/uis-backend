package by.kovzov.uis.service.impl;

import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import by.kovzov.uis.repository.api.UserRepository;
import by.kovzov.uis.repository.mybatis.impl.UserRepositoryImpl;
import by.kovzov.uis.domain.model.user.User;
import by.kovzov.uis.service.api.UserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Optional<User> findById(String id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User save(User user) {
        if (Objects.isNull(user.getId())) {
            userRepository.create(user);
        }
        return user;
    }

    private void checkUniqueUsername() {

    }
}
