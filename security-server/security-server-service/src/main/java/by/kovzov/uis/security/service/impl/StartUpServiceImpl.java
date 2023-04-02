package by.kovzov.uis.security.service.impl;

import static org.apache.commons.lang3.Validate.notBlank;

import java.time.LocalDateTime;
import java.util.Set;

import by.kovzov.uis.security.repository.api.UserRepository;
import by.kovzov.uis.security.repository.entity.Role;
import by.kovzov.uis.security.repository.entity.User;
import by.kovzov.uis.security.service.api.RoleService;
import by.kovzov.uis.security.service.api.StartUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StartUpServiceImpl implements StartUpService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    private String adminUsername;
    private String adminPassword;

    @Value("${uis.security.admin-username}")
    public void setAdminUsername(String adminUsername) {
        this.adminUsername = notBlank(adminUsername, "adminUsername can not be blank");
    }

    @Value("${uis.security.admin-password}")
    public void setAdminPassword(String adminPassword) {
        notBlank(adminPassword, "adminPassword can not be blank");
        this.adminPassword = passwordEncoder.encode(adminPassword);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void updateAdminUser() {
        userRepository.findByUsername(adminUsername)
            .ifPresentOrElse(
                user -> user.setPassword(passwordEncoder.encode(adminPassword)),
                () -> userRepository.save(buildAdminUser())
            );
    }

    private User buildAdminUser() {
        User user = new User();
        user.setCreationTime(LocalDateTime.now());
        user.setPassword(adminPassword);
        user.setUsername(adminUsername);
        user.setRoles(Set.of(getAdminRole()));
        return user;
    }

    private Role getAdminRole() {
        Long adminRoleId = roleService.search("admin", Pageable.ofSize(1))
            .getContent().get(0).getId();
        Role adminRole = new Role();
        adminRole.setId(adminRoleId);
        return adminRole;
    }
}
