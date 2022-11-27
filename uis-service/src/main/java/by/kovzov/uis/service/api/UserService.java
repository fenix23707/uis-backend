package by.kovzov.uis.service.api;

import by.kovzov.uis.domain.entity.User;

public interface UserService {

    User getById(String id);

    User getByUsername(String username);

    User create(User user);
}
