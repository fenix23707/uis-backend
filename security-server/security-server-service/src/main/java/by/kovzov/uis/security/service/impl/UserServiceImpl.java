package by.kovzov.uis.security.service.impl;

import static java.text.MessageFormat.format;

import java.time.LocalDateTime;
import java.util.Objects;

import by.kovzov.uis.common.exception.AlreadyExistsException;
import by.kovzov.uis.common.exception.NotFoundException;
import by.kovzov.uis.security.dto.UserDto;
import by.kovzov.uis.security.repository.api.UserRepository;
import by.kovzov.uis.security.repository.entity.Role;
import by.kovzov.uis.security.repository.entity.User;
import by.kovzov.uis.security.repository.specification.UserSpecifications;
import by.kovzov.uis.security.service.api.RoleService;
import by.kovzov.uis.security.service.api.UserService;
import by.kovzov.uis.security.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    private final UserMapper userMapper;

    @Override
    public UserDto getDtoById(Long id) {
        return userMapper.toDto(getById(id));
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

    @Override
    public void grantRole(Long userId, Long roleId) {
        User user = getById(userId);
        if (isUserHasRole(user, roleId)) {
            throw new AlreadyExistsException(
                "User with id = %d already has role with id = %d".formatted(user.getId(), roleId));
        }
        Role role = roleService.getById(roleId);
        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Override
    public void revokeRole(Long userId, Long roleId) {
        User user = getById(userId);
        if (!isUserHasRole(user, roleId)) {
            throw new NotFoundException(
                "User with id = %d does not have role with id = %d".formatted(user.getId(), roleId));
        }
        Role role = roleService.getById(roleId);
        user.getRoles().remove(role);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUserLastActivity(Long userId) {
        User user = getById(userId);
        user.setLastActivity(LocalDateTime.now());
    }

    private User getById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("User with id = %s not found.".formatted(id)));
    }

    private boolean isUserHasRole(User user, Long roleId) {
        return user.getRoles().stream()
            .map(Role::getId)
            .anyMatch(id -> Objects.equals(id, roleId));
    }
}
