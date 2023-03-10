package by.kovzov.uis.security.rest.controller;


import by.kovzov.uis.security.dto.JwtAuthenticationDto;
import by.kovzov.uis.security.dto.LoginDto;
import by.kovzov.uis.security.dto.RefreshTokenDto;
import by.kovzov.uis.security.rest.security.service.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/security/tokens")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;
    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final JwtAuthenticationProvider jwtRefreshTokenAuthProvider;

    @PostMapping("/create")
    public JwtAuthenticationDto create(@RequestBody @Valid LoginDto loginDto) {
        Authentication authentication = daoAuthenticationProvider.authenticate(
            UsernamePasswordAuthenticationToken.unauthenticated(loginDto.getUsername(), loginDto.getPassword()));

        return tokenService.createTokens(authentication);
    }

    @PostMapping("/refresh")
    public JwtAuthenticationDto refresh(@RequestBody RefreshTokenDto refreshTokenDto) {
        Authentication authentication = jwtRefreshTokenAuthProvider.authenticate(
            new BearerTokenAuthenticationToken(refreshTokenDto.getRefreshToken()));
        return tokenService.createTokens(authentication);
    }
}
