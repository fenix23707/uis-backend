package by.kovzov.uis.service.api;

import java.util.Optional;

import by.kovzov.uis.domain.model.User;

public interface IUserService {

    Optional<User> findById(String id);
}
