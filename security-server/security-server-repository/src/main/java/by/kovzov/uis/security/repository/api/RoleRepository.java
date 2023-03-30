package by.kovzov.uis.security.repository.api;

import by.kovzov.uis.security.repository.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
