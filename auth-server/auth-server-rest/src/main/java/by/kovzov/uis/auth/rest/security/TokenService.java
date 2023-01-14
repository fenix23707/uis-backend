package by.kovzov.uis.auth.rest.security;

import org.springframework.security.core.Authentication;

import by.kovzov.uis.auth.domain.dto.JwtAuthenticationDto;

public interface TokenService {


    JwtAuthenticationDto createToken(Authentication authentication);
}
