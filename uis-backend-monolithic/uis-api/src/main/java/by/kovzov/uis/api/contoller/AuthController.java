package by.kovzov.uis.api.contoller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import by.kovzov.uis.api.security.TokenService;
import by.kovzov.uis.api.security.UserSecurity;
import by.kovzov.uis.domain.dto.auth.LoginDto;
import by.kovzov.uis.domain.dto.auth.RefreshTokenDto;
import by.kovzov.uis.domain.dto.auth.SignupDto;
import by.kovzov.uis.service.api.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final TokenService tokenGenerator;
    private final AuthService authService;
    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final JwtAuthenticationProvider jwtRefreshTokenAuthProvider;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody SignupDto signupDto) {
        UserSecurity user = UserSecurity.from(authService.signup(signupDto));
        Authentication authentication = UsernamePasswordAuthenticationToken
            .authenticated(user, signupDto.getPassword(), user.getAuthorities());
        return ResponseEntity.ok(tokenGenerator.createToken(authentication));
    }

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody LoginDto loginDto) {
        Authentication authentication = daoAuthenticationProvider.authenticate(
            UsernamePasswordAuthenticationToken.unauthenticated(loginDto.getUsername(), loginDto.getPassword()));

        return ResponseEntity.ok(tokenGenerator.createToken(authentication));
    }

    @PostMapping("/refresh")
    public ResponseEntity refresh(@RequestBody RefreshTokenDto refreshTokenDto) {
        Authentication authentication =
            jwtRefreshTokenAuthProvider.authenticate(new BearerTokenAuthenticationToken(refreshTokenDto.getRefreshToken()));
        return ResponseEntity.ok(tokenGenerator.createToken(authentication));
    }
}
