package by.kovzov.uis.security.service.mapper;

import by.kovzov.uis.security.dto.PermissionDto;
import by.kovzov.uis.security.repository.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = MethodMapper.class)
public interface PermissionMapper {

    Permission toEntity(PermissionDto permissionDto);
}
