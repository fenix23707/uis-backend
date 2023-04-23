package by.kovzov.uis.security.service.impl;

import java.util.Objects;

import by.kovzov.uis.security.service.api.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

@Service("authorizationService")
public class AuthorizationServiceImpl implements AuthorizationService {

    private final String userIdClaimName;

    public AuthorizationServiceImpl(@Value("${uis.claim-name.user-id}") String userIdClaimName) {
        this.userIdClaimName = userIdClaimName;
    }

    @Override
    public boolean hasSameId(Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken jwtToken
            && jwtToken.getPrincipal() instanceof Jwt jwt) {

            return Objects.equals(jwt.getClaim(userIdClaimName), userId);
        }
        return false;
    }
}
