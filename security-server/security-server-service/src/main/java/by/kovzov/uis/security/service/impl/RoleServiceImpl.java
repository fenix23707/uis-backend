package by.kovzov.uis.security.service.impl;


import by.kovzov.uis.security.dto.RoleDto;
import by.kovzov.uis.security.repository.api.RoleRepository;
import by.kovzov.uis.security.repository.entity.Role;
import by.kovzov.uis.security.service.api.RoleService;
import by.kovzov.uis.security.service.mapper.RoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleDto create(RoleDto requestDto) {
        Role entity = roleMapper.toEntity(requestDto);
        return roleMapper.toDto(roleRepository.save(entity));
    }
}
