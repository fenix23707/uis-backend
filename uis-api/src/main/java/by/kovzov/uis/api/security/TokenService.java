package by.kovzov.uis.api.security;

import org.springframework.security.core.Authentication;

import by.kovzov.uis.domain.dto.response.JwtAuthenticationResponse;

public interface TokenService {


    JwtAuthenticationResponse createToken(Authentication authentication);
}
