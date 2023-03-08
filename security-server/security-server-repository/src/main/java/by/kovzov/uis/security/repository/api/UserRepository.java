package by.kovzov.uis.security.repository.api;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import by.kovzov.uis.security.repository.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findByUsername(String username);
}
