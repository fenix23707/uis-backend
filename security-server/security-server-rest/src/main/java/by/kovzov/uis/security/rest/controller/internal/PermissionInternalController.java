package by.kovzov.uis.security.rest.controller.internal;

import java.util.List;

import by.kovzov.uis.security.dto.PermissionDto;
import by.kovzov.uis.security.service.api.PermissionService;
import by.kovzov.uis.security.service.api.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/internal/permissions")
@RequiredArgsConstructor
public class PermissionInternalController {

    private final PermissionService permissionService;
    private final RoleService roleService;

    @PostMapping("/{applicationName}")
    public void save(@PathVariable String applicationName, @RequestBody @Valid List<PermissionDto> permissions) {
        permissionService.update(permissions, applicationName);
        roleService.updateAdminRole(); // temp solution (this line should not be here)
    }
}
