package by.kovzov.uis.auth.service.api;


import by.kovzov.uis.auth.domain.entity.User;

public interface UserService {

    User getById(String id);

    User getByUsername(String username);

    User create(User user);
}
