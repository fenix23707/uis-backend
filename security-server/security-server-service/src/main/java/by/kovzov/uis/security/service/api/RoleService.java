package by.kovzov.uis.security.service.api;

import by.kovzov.uis.security.dto.RoleDto;
import by.kovzov.uis.security.dto.RoleEditDto;
import by.kovzov.uis.security.repository.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleService {

    Role getById(Long id);

    Role getByName(String name);

    Page<RoleDto> search(String name, Pageable pageable);

    RoleDto create(RoleEditDto requestDto);

    RoleDto update(Long id, RoleEditDto dto);

    void updateAdminRole();
}
