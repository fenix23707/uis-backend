package by.kovzov.uis.security.service.api;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import by.kovzov.uis.security.dto.MethodDto;
import by.kovzov.uis.security.repository.entity.Method;
import by.kovzov.uis.security.repository.entity.Permission;

public interface MethodService {

    void updateMethodsByPermission(Permission permission, Collection<MethodDto> methods);

    void deleteAllExcept(Collection<MethodDto> methodsNotDelete);

    void deleteAll(Collection<Method> methods);

    Optional<Permission> findByMethods(List<Permission> existingPermissions, Collection<MethodDto> methods);
}
