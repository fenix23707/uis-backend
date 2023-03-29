package by.kovzov.uis.security.rest.controller;

import by.kovzov.uis.security.dto.UserDto;
import by.kovzov.uis.security.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    @PreAuthorize("hasAuthority('USER_READ')")
    public UserDto getById(@PathVariable Long id) {
        return userService.getById(id);
    }
}
