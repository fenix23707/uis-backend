package by.kovzov.uis.security.rest.controller;

import by.kovzov.uis.security.dto.RoleDto;
import by.kovzov.uis.security.service.api.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public RoleDto create(@RequestBody @Valid RoleDto requestDto) {
        return roleService.create(requestDto);
    }
}
