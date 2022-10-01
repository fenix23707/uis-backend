package by.kovzov.uis.repository.api;

import java.util.Optional;

import by.kovzov.uis.domain.model.user.User;

public interface UserRepository {

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    Long create(User user);
}
