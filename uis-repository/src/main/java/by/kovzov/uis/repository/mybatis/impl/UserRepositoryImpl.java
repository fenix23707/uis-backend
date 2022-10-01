package by.kovzov.uis.repository.mybatis.impl;

import org.springframework.stereotype.Repository;

import java.util.Optional;

import by.kovzov.uis.domain.model.user.User;
import by.kovzov.uis.repository.api.UserRepository;
import by.kovzov.uis.repository.mybatis.mapper.UserMapper;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserMapper userMapper;

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(userMapper.findById(id));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(userMapper.findByUsername(username));
    }

    @Override
    public Long create(User user) {
        return null;
    }
}
