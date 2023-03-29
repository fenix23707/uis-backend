package by.kovzov.uis.security.service.impl;

import static java.text.MessageFormat.format;

import by.kovzov.uis.common.exception.NotFoundException;
import by.kovzov.uis.security.dto.UserDto;
import by.kovzov.uis.security.repository.api.UserRepository;
import by.kovzov.uis.security.repository.entity.User;
import by.kovzov.uis.security.repository.specification.UserSpecifications;
import by.kovzov.uis.security.service.api.UserService;
import by.kovzov.uis.security.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public UserDto getById(Long id) {
        return userRepository.findById(id)
            .map(userMapper::toDto)
            .orElseThrow(() -> new NotFoundException("User with id = %s not found.".formatted(id)));
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new NotFoundException(format("User with username = {0} not found.", username)));
    }

    @Override
    public Page<UserDto> search(String username, Pageable pageable) {
        Specification<User> userSpecification = UserSpecifications.usernameLike(username);
        return userRepository.findAll(userSpecification, pageable)
            .map(userMapper::toDto);
    }
}
