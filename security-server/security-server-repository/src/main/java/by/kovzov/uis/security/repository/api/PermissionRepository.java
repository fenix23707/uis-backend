package by.kovzov.uis.security.repository.api;

import java.util.List;
import java.util.Set;

import by.kovzov.uis.security.repository.entity.Permission;
import by.kovzov.uis.security.repository.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    @Query("from Permission p join p.roles r where r.id in :roleIds")
    Set<Permission> findAllByRoles(List<Role> roleIds);
}
