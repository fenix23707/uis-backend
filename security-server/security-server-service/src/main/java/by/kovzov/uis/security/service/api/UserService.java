package by.kovzov.uis.security.service.api;


import by.kovzov.uis.security.repository.entity.User;

public interface UserService {

    User getById(String id);

    User getByUsername(String username);

    User create(User user);
}
