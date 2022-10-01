package by.kovzov.uis.api.contoller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import by.kovzov.uis.api.security.UserSecurity;
import by.kovzov.uis.service.api.UserService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/profiles")
@AllArgsConstructor
public class ProfileController {

    private UserService userService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String getProfile(@AuthenticationPrincipal UserSecurity user, @PathVariable("id") Long id) {
        return String.valueOf(id);
    }
}
