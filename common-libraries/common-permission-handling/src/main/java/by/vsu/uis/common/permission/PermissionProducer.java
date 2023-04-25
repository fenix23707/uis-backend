package by.vsu.uis.common.permission;

import java.util.List;

import by.kovzov.uis.security.dto.PermissionDto;

public interface PermissionProducer {

    List<PermissionDto> producePermissions();
}
