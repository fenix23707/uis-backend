package by.kovzov.uis.security.rest.controller;

import by.kovzov.uis.security.dto.UserCreateDto;
import by.kovzov.uis.security.dto.UserDto;
import by.kovzov.uis.security.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('USER_SEARCH')")
    public Page<UserDto> search(@RequestParam(required = false, defaultValue = "") String username,
                                @PageableDefault(sort = "username", direction = Direction.ASC) Pageable pageable) {
        return userService.search(username, pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_READ') or @authorizationService.hasSameId(#id)")
    public UserDto getById(@PathVariable Long id) {
        return userService.getDtoById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('USER_CREATE')")
    public UserDto create(@RequestBody UserCreateDto userDto) {
        return userService.create(userDto);
    }

    @PostMapping("/{userId}/roles/{roleId}")
    @PreAuthorize("hasAuthority('USER_MANAGE_ROLES')")
    public void grantRole(@PathVariable Long userId,
                          @PathVariable Long roleId) {
        userService.grantRole(userId, roleId);
    }

    @DeleteMapping("/{userId}/roles/{roleId}")
    @PreAuthorize("hasAuthority('USER_MANAGE_ROLES')")
    public void revokeRole(@PathVariable Long userId,
                           @PathVariable Long roleId) {
        userService.revokeRole(userId, roleId);
    }
}
