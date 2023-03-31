package by.kovzov.uis.security.service.impl;


import by.kovzov.uis.common.validator.unique.UniqueValidationService;
import by.kovzov.uis.security.dto.RoleDto;
import by.kovzov.uis.security.repository.api.RoleRepository;
import by.kovzov.uis.security.repository.entity.Role;
import by.kovzov.uis.security.service.api.RoleService;
import by.kovzov.uis.security.service.mapper.RoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final UniqueValidationService uniqueValidationService;

    @Override
    public Page<RoleDto> search(String name, Pageable pageable) {
        return roleRepository.findAllByNameLike(name, pageable)
            .map(roleMapper::toDto);
    }

    @Override
    public RoleDto create(RoleDto requestDto) {
        Role entity = roleMapper.toEntity(requestDto);
        uniqueValidationService.checkEntity(entity, roleRepository);
        return roleMapper.toDto(roleRepository.save(entity));
    }
}
