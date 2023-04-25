package by.vsu.uis.security.permission;

import java.util.List;

import by.kovzov.uis.security.dto.PermissionDto;

public interface PermissionProducer {

    List<PermissionDto> producePermissions();
}
