package by.kovzov.uis.security.rest.security.service;

import org.springframework.security.core.Authentication;

import by.kovzov.uis.security.dto.JwtAuthenticationDto;

public interface TokenService {


    JwtAuthenticationDto createTokens(Authentication authentication);
}
