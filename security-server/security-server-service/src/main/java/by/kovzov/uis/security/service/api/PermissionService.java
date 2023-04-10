package by.kovzov.uis.security.service.api;

import java.util.List;

import by.kovzov.uis.security.dto.PermissionDto;
import org.springframework.data.domain.Sort;

public interface PermissionService {

    List<PermissionDto> getAll(Sort sort);
}
