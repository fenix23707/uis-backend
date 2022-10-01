package by.kovzov.uis.service.impl;

import org.springframework.stereotype.Service;

import java.util.Optional;

import by.kovzov.uis.repository.api.UserRepository;
import by.kovzov.uis.repository.mybatis.impl.UserRepositoryImpl;
import by.kovzov.uis.domain.model.user.User;
import by.kovzov.uis.service.api.UserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

//    private static Map<Long, User> userMap = new HashMap<>();

    private final UserRepository userRepository;

//
//    public UserServiceImpl() {
//        User user = new User();
//        user.setId(1L);
//        user.setUsername("username");
//        user.setPassword(new BCryptPasswordEncoder().encode("root"));
//        user.setRoles(Set.of(new UserRole(1L, UserRoleName.ROLE_ADMIN)));
//        user.setStatus(UserStatus.ACTIVE);
//        userMap.put(1L, user);
//    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByUsername(String username) {
//        return Optional.of(userMap.get(1L));
        return userRepository.findByUsername(username);
    }

    @Override
    public User save(User user) {
//        userMap.put((long) (userMap.size() + 1), user);
        return user;
    }

    private void checkUniqueUsername() {

    }
}
