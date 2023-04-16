package by.kovzov.uis.security.rest.controller;

import by.kovzov.uis.security.dto.AuthorizationDto;
import by.kovzov.uis.security.service.api.AuthorizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authorization")
@RequiredArgsConstructor
public class AuthorizationController {

    private final AuthorizationService authorizationService;

    @PostMapping
    public void authorize(@RequestBody @Valid AuthorizationDto authorizationDto) {
        authorizationService.verifyAccess(authorizationDto);
    }
}
