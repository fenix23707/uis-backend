package by.kovzov.uis.security.service.api;

import by.kovzov.uis.security.dto.RoleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleService {

    Page<RoleDto> search(String name, Pageable pageable);

    RoleDto create(RoleDto requestDto);
}
