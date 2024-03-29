package by.kovzov.uis.security.service.impl;

import static org.apache.commons.lang3.Validate.notBlank;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import by.kovzov.uis.security.repository.api.UserRepository;
import by.kovzov.uis.security.repository.entity.User;
import by.kovzov.uis.security.service.api.PermissionService;
import by.kovzov.uis.security.service.api.RoleService;
import by.kovzov.uis.security.service.api.StartUpService;
import by.vsu.uis.security.permission.PermissionProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
    private final List<PermissionProducer> permissionProducers;
    private final PermissionService permissionService;

    private String adminUsername;
    private String adminPassword;
    private String adminRoleName;
    private String applicationName;

    @Value("${uis.security.admin-username}")
    public void setAdminUsername(String adminUsername) {
        this.adminUsername = notBlank(adminUsername, "adminUsername can not be blank");
    }

    @Value("${uis.security.admin-password}")
    public void setAdminPassword(String adminPassword) {
        notBlank(adminPassword, "adminPassword can not be blank");
        this.adminPassword = passwordEncoder.encode(adminPassword);
    }

    @Value("${uis.security.admin-role-name}")
    public void setAdminRoleName(String adminRoleName) {
        this.adminRoleName = notBlank(adminRoleName, "adminRoleName can not be blank");;
    }

    @Value("${spring.application.name:undefined}")
    public void setApplicationName(String applicationName) {
        this.applicationName = notBlank(applicationName, "application name can not be blank");
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void updateAdminUser() {
        roleService.updateAdminRole();
        userRepository.findByUsername(adminUsername)
            .ifPresentOrElse(
                user -> user.setPassword(adminPassword),
                () -> userRepository.save(buildAdminUser())
            );
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void updatePermissions() {
        var permissions = permissionProducers.stream()
            .map(PermissionProducer::producePermissions)
            .flatMap(Collection::stream)
            .toList();
        permissionService.update(permissions, applicationName);
    }

    private User buildAdminUser() {
        User user = new User();
        user.setCreationTime(LocalDateTime.now());
        user.setPassword(adminPassword);
        user.setUsername(adminUsername);
        user.setRoles(Set.of(roleService.getByName(adminRoleName)));
        return user;
    }
}
