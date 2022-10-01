package by.kovzov.uis.service.api;

import java.util.Optional;

import by.kovzov.uis.domain.model.user.User;

public interface UserService {

    Optional<User> findById(String id);

    Optional<User> findByUsername(String username);

    User save(User user);
}
