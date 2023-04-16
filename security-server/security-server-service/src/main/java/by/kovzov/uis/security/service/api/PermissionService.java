package by.kovzov.uis.security.service.api;

import java.util.List;
import java.util.Set;

import by.kovzov.uis.security.dto.PermissionDto;
import by.kovzov.uis.security.repository.entity.Permission;
import org.springframework.data.domain.Sort;

public interface PermissionService {

    List<PermissionDto> getAll(Sort sort);

    Set<Permission> getAllByRolesIds(List<Long> roleIds);
}
