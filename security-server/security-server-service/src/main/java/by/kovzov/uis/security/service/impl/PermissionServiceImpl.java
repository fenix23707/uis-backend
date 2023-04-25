package by.kovzov.uis.security.service.impl;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Map.Entry;

import by.kovzov.uis.security.dto.ActionDto;
import by.kovzov.uis.security.dto.PermissionDto;
import by.kovzov.uis.security.dto.GroupedPermission;
import by.kovzov.uis.security.repository.api.PermissionRepository;
import by.kovzov.uis.security.repository.entity.Permission;
import by.kovzov.uis.security.service.api.PermissionService;
import by.kovzov.uis.security.service.mapper.PermissionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;

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
    public void saveIfNotExists(List<PermissionDto> permissions) {
        permissions.forEach(this::saveIfNotExists);
    }

    @Override
    public void saveIfNotExists(PermissionDto dto) {
        Permission entity = permissionMapper.toEntity(dto);
        permissionRepository.findByScopeAndAction(dto.getScope(), dto.getAction())
            .orElseGet(() -> permissionRepository.save(entity));
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
