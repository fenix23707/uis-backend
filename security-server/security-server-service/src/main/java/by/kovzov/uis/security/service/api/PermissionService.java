package by.kovzov.uis.security.service.api;

import java.util.List;

import by.kovzov.uis.security.dto.PermissionDto;
import by.kovzov.uis.security.dto.GroupedPermission;
import by.kovzov.uis.security.repository.entity.Permission;
import org.springframework.data.domain.Sort;

public interface PermissionService {

    List<GroupedPermission> getAllGroupedPermissions(Sort sort);

    List<Permission> getAllPermissions();

    void update(List<PermissionDto> permissions, String applicationName);
}
