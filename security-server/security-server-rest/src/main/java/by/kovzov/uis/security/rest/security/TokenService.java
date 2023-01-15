package by.kovzov.uis.security.rest.security;

import org.springframework.security.core.Authentication;

import by.kovzov.uis.security.domain.dto.JwtAuthenticationDto;

public interface TokenService {


    JwtAuthenticationDto createTokens(Authentication authentication);
}
