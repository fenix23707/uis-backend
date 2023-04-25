package by.kovzov.uis.security.service.impl;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import by.kovzov.uis.common.exception.NotFoundException;
import by.kovzov.uis.common.validator.unique.UniqueValidationService;
import by.kovzov.uis.security.dto.RoleDto;
import by.kovzov.uis.security.dto.RoleEditDto;
import by.kovzov.uis.security.repository.api.RoleRepository;
import by.kovzov.uis.security.repository.entity.Permission;
import by.kovzov.uis.security.repository.entity.Role;
import by.kovzov.uis.security.service.api.PermissionService;
import by.kovzov.uis.security.service.api.RoleService;
import by.kovzov.uis.security.service.mapper.RoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final UniqueValidationService uniqueValidationService;
    private final PermissionService permissionService;

    private final String adminRoleName;


    public RoleServiceImpl(RoleRepository roleRepository,
                           RoleMapper roleMapper,
                           UniqueValidationService uniqueValidationService,
                           PermissionService permissionService,
                           @Value("${uis.security.admin-role-name}") String adminRoleName) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
        this.uniqueValidationService = uniqueValidationService;
        this.permissionService = permissionService;
        this.adminRoleName = adminRoleName;
    }

    @Override
    public Role getById(Long id) {
        return roleRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Role with id = %d not found.".formatted(id)));
    }

    @Override
    public Role getByName(String name) {
        return roleRepository.findByName(name)
            .orElseThrow(() -> new NotFoundException("Role with name = %d not found.".formatted(name)));
    }

    @Override
    public Page<RoleDto> search(String name, Pageable pageable) {
        return roleRepository.findAllByNameLike(name, pageable)
            .map(roleMapper::toDto);
    }

    @Override
    public RoleDto create(RoleEditDto requestDto) {
        Role entity = roleMapper.toEntity(requestDto);
        uniqueValidationService.checkEntity(entity, roleRepository);
        return roleMapper.toDto(roleRepository.save(entity));
    }

    @Override
    public RoleDto update(Long id, RoleEditDto dto) {
        getById(id);
        Role entity = roleMapper.toEntity(dto);
        entity.setId(id);
        uniqueValidationService.checkEntity(entity, roleRepository);
        return roleMapper.toDto(roleRepository.save(entity));
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void updateAdminRole() {
        Set<Permission> permissions = new HashSet<>(permissionService.getAllPermissions());
        roleRepository.findByName(adminRoleName).ifPresentOrElse(
            role -> role.setPermissions(permissions),
            () -> roleRepository.save(new Role(adminRoleName, permissions))
        );
    }
}
