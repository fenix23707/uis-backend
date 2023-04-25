package by.kovzov.uis.security.rest.controller;

import by.kovzov.uis.security.dto.RoleDto;
import by.kovzov.uis.security.dto.RoleEditDto;
import by.kovzov.uis.security.service.api.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('ROLE_READ')")
    public Page<RoleDto> search(@RequestParam(defaultValue = "") String name,
                                @PageableDefault(sort = "name", direction = Direction.ASC) Pageable pageable) {
        return roleService.search(name, pageable);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_CREATE')")
    public RoleDto create(@RequestBody @Valid RoleEditDto requestDto) {
        return roleService.create(requestDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_UPDATE')")
    public RoleDto update(@PathVariable Long id, @RequestBody @Valid RoleEditDto dto) {
        return roleService.update(id, dto);
    }
}
