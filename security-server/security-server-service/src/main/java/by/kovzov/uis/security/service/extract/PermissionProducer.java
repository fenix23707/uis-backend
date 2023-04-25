package by.kovzov.uis.security.service.extract;

import java.util.List;

import by.kovzov.uis.security.dto.PermissionDto;

public interface PermissionProducer {

    List<PermissionDto> producePermissions();
}
