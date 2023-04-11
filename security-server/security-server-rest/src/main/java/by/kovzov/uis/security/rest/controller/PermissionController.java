package by.kovzov.uis.security.rest.controller;

import java.util.List;

import by.kovzov.uis.security.dto.PermissionDto;
import by.kovzov.uis.security.service.api.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @GetMapping
    @PreAuthorize("hasAuthority('PERMISSION_GET')")
    public List<PermissionDto> getAll(@SortDefault("scope") Sort sort) {
        return permissionService.getAll(sort);
    }
}
