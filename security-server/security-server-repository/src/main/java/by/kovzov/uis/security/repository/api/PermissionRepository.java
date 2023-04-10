package by.kovzov.uis.security.repository.api;

import by.kovzov.uis.security.repository.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
