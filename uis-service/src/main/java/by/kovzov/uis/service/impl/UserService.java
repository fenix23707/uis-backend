package by.kovzov.uis.service.impl;

import org.springframework.stereotype.Service;

import java.util.Optional;

import by.kovzov.uis.domain.model.User;
import by.kovzov.uis.service.api.IUserService;

@Service
public class UserService implements IUserService {

    @Override
    public Optional<User> findById(String id) {
        return Optional.empty();
    }
}
