package by.kovzov.uis.repository.api;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import by.kovzov.uis.domain.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
