package by.kovzov.uis.security.service.api;

import java.util.List;

import by.kovzov.uis.security.dto.JwtAuthenticationDto;
import org.springframework.security.core.Authentication;

public interface TokenService {


    JwtAuthenticationDto createTokens(Authentication authentication);

    List<Long> extractRoleIds(String accessToken);
}
