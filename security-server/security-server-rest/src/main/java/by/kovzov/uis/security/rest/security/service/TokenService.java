package by.kovzov.uis.security.rest.security.service;

import by.kovzov.uis.security.dto.JwtAuthenticationDto;
import org.springframework.security.core.Authentication;

public interface TokenService {


    JwtAuthenticationDto createTokens(Authentication authentication);
}
