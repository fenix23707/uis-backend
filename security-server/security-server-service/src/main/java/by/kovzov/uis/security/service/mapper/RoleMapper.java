package by.kovzov.uis.security.service.mapper;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import by.kovzov.uis.security.dto.RoleDto;
import by.kovzov.uis.security.repository.entity.Permission;
import by.kovzov.uis.security.repository.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mappings({
        @Mapping(source = "permissions", target = "permissionIds", qualifiedByName = "toPermissionIds")
    })
    RoleDto toDto(Role role);

    @Mappings({
        @Mapping(source = "permissionIds", target = "permissions", qualifiedByName = "toPermissions")
    })
    Role toEntity(RoleDto roleDto);

    @Named("toPermissionIds")
    default List<Integer> toPermissionIds(Set<Permission> permissions) {
        return permissions.stream()
            .map(Permission::getId)
            .collect(Collectors.toList());
    }

    @Named("toPermissions")
    default Set<Permission> toPermissions(List<Integer> permissionIds) {
        return permissionIds.stream()
            .map(id -> {
                Permission permission = new Permission();
                permission.setId(id);
                return permission;
            })
            .collect(Collectors.toSet());
    }
}
