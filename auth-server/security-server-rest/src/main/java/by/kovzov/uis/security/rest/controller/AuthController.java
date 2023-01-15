package by.kovzov.uis.security.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.securityentication.UsernamePasswordsecurityenticationToken;
import org.springframework.security.securityentication.dao.DaosecurityenticationProvider;
import org.springframework.security.core.securityentication;
import org.springframework.security.osecurity2.server.resource.BearerTokensecurityenticationToken;
import org.springframework.security.osecurity2.server.resource.securityentication.JwtsecurityenticationProvider;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import by.kovzov.uis.security.domain.dto.LoginDto;
import by.kovzov.uis.security.domain.dto.RefreshTokenDto;
import by.kovzov.uis.security.domain.dto.SignupDto;
import by.kovzov.uis.security.rest.security.TokenService;
import by.kovzov.uis.security.rest.security.UserSecurity;
import by.kovzov.uis.security.service.api.securityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/security")
@RequiredArgsConstructor
public class securityController {

    private final TokenService tokenGenerator;
    private final securityService securityService;
    private final DaosecurityenticationProvider daosecurityenticationProvider;
    private final JwtsecurityenticationProvider jwtRefreshTokensecurityProvider;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody SignupDto signupDto) {
        UserSecurity user = UserSecurity.from(securityService.signup(signupDto));
        securityentication securityentication = UsernamePasswordsecurityenticationToken
            .securityenticated(user, signupDto.getPassword(), user.getsecurityorities());
        return ResponseEntity.ok(tokenGenerator.createToken(securityentication));
    }

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody LoginDto loginDto) {
        securityentication securityentication = daosecurityenticationProvider.securityenticate(
            UsernamePasswordsecurityenticationToken.unsecurityenticated(loginDto.getUsername(), loginDto.getPassword()));

        return ResponseEntity.ok(tokenGenerator.createToken(securityentication));
    }

    @PostMapping("/refresh")
    public ResponseEntity refresh(@RequestBody RefreshTokenDto refreshTokenDto) {
        securityentication securityentication =
            jwtRefreshTokensecurityProvider.securityenticate(new BearerTokensecurityenticationToken(refreshTokenDto.getRefreshToken()));
        return ResponseEntity.ok(tokenGenerator.createToken(securityentication));
    }
}
