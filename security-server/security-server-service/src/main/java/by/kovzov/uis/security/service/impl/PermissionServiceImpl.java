package by.kovzov.uis.security.service.impl;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import by.kovzov.uis.security.dto.ActionDto;
import by.kovzov.uis.security.dto.GroupedPermission;
import by.kovzov.uis.security.dto.PermissionDto;
import by.kovzov.uis.security.repository.api.PermissionRepository;
import by.kovzov.uis.security.repository.entity.Method;
import by.kovzov.uis.security.repository.entity.Permission;
import by.kovzov.uis.security.service.api.MethodService;
import by.kovzov.uis.security.service.api.PermissionService;
import by.kovzov.uis.security.service.mapper.PermissionMapper;
import by.kovzov.uis.security.service.util.CollectionsUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;
    private final MethodService methodService;

    @Override
    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    @Override
    public List<GroupedPermission> getAllGroupedPermissions(Sort sort) {
        return permissionRepository.findAll(sort)
            .stream()
            .collect(groupingBy(
                Permission::getScope,
                mapping(this::toActionDto, toList())
            ))
            .entrySet().stream()
            .map(this::toPermissionDto)
            .toList();
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void update(List<PermissionDto> permissions, String applicationName) {
        log.info("Started updating permissions for {}", applicationName);

        var existingPermissions = permissionRepository.findAllWithMethodsByApplicationName(applicationName);
        var permissionsToDelete = new LinkedList<>(existingPermissions);
        Set<Method> methodsToDelete = existingPermissions.stream()
            .map(Permission::getMethods)
            .flatMap(Collection::stream)
            .collect(Collectors.toSet());
        log.info("Existing permissions: {}", existingPermissions);

        for (PermissionDto permissionDto : permissions) {
            Permission permission;

            var permissionByScopeAndAction = findPermissionByScopeAndAction(existingPermissions, permissionDto);

            if (permissionByScopeAndAction.isPresent()) {
                permission = permissionByScopeAndAction.get();
            } else {
                var permissionByMethods = methodService.findByMethods(existingPermissions, permissionDto.getMethods());
                permission = permissionByMethods.orElseGet(() -> {
                    Permission newPermission = permissionMapper.toEntity(permissionDto);
                    newPermission.setApplicationName(applicationName);
                    return newPermission;
                });
            }

            updatePermission(permission, permissionDto);
            permissionsToDelete.remove(permission);
            methodsToDelete.removeAll(permission.getMethods());
        }

        log.info("Removing following permissions: {}", permissionsToDelete);
        permissionRepository.flush();
        permissionRepository.deleteAllIn(permissionsToDelete);

        log.info("Removing following methods: {}", methodsToDelete);
        methodService.deleteAll(methodsToDelete);

        log.info("Finished updating permission for {}.", applicationName);
    }

    private Optional<Permission> findPermissionByScopeAndAction(List<Permission> existingPermissions, PermissionDto dto) {
        Predicate<Permission> isEqualsByScopeAndAction = permission -> new EqualsBuilder()
            .append(permission.getAction(), dto.getAction())
            .append(permission.getScope(), dto.getScope())
            .isEquals();
        return existingPermissions.stream()
            .filter(isEqualsByScopeAndAction)
            .collect(CollectionsUtil.toSingleElement("Found more than one permission by scope and action for dto: %s", dto));
    }

    private void updatePermission(Permission existingPermission, PermissionDto permissionDto) {
        existingPermission.setScope(permissionDto.getScope());
        existingPermission.setAction(permissionDto.getAction());
        var saved = permissionRepository.save(existingPermission);

        methodService.updateMethodsByPermission(saved, permissionDto.getMethods());
    }

    private ActionDto toActionDto(Permission permission) {
        return ActionDto.builder()
            .id(permission.getId())
            .name(permission.getAction())
            .build();
    }

    private GroupedPermission toPermissionDto(Entry<String, List<ActionDto>> entry) {
        return GroupedPermission.builder()
            .scope(entry.getKey())
            .actions(entry.getValue())
            .build();
    }
}
