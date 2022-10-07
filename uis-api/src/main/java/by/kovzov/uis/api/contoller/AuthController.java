package by.kovzov.uis.api.contoller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import by.kovzov.uis.api.security.TokenService;
import by.kovzov.uis.api.security.UserSecurity;
import by.kovzov.uis.domain.dto.request.LoginRequest;
import by.kovzov.uis.domain.dto.request.RefreshTokenRequest;
import by.kovzov.uis.domain.dto.request.SignupRequest;
import by.kovzov.uis.domain.model.user.User;
import by.kovzov.uis.service.api.AuthService;
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
    public ResponseEntity signup(@RequestBody SignupRequest signupRequest) {
        UserSecurity user = UserSecurity.from(authService.signup(signupRequest));
        Authentication authentication = UsernamePasswordAuthenticationToken
            .authenticated(user, signupRequest.getPassword(), user.getAuthorities());
        return ResponseEntity.ok(tokenGenerator.createToken(authentication));
    }

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = daoAuthenticationProvider.authenticate(
            UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.getUsername(), loginRequest.getPassword()));

        return ResponseEntity.ok(tokenGenerator.createToken(authentication));
    }

    @PostMapping("/refresh")
    public ResponseEntity refresh(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        Authentication authentication =
            jwtRefreshTokenAuthProvider.authenticate(new BearerTokenAuthenticationToken(refreshTokenRequest.getRefreshToken()));
        return ResponseEntity.ok(tokenGenerator.createToken(authentication));
    }
}
