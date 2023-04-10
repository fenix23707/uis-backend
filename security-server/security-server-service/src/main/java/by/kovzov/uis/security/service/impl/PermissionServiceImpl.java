package by.kovzov.uis.security.service.impl;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import by.kovzov.uis.security.dto.ActionDto;
import by.kovzov.uis.security.dto.PermissionDto;
import by.kovzov.uis.security.repository.api.PermissionRepository;
import by.kovzov.uis.security.repository.entity.Permission;
import by.kovzov.uis.security.service.api.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    @Override
    public List<PermissionDto> getAll(Sort sort) {
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

    private ActionDto toActionDto(Permission permission) {
        return ActionDto.builder()
            .id(permission.getId())
            .name(permission.getAction())
            .build();
    }

    private PermissionDto toPermissionDto(Entry<String, List<ActionDto>> entry) {
        return PermissionDto.builder()
            .scope(entry.getKey())
            .actions(entry.getValue())
            .build();
    }
}
