package by.kovzov.uis.api.security;

import org.springframework.security.core.Authentication;

import by.kovzov.uis.domain.dto.auth.JwtAuthenticationDto;

public interface TokenService {


    JwtAuthenticationDto createToken(Authentication authentication);
}
