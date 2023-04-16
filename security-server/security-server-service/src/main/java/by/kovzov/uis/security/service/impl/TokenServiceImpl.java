package by.kovzov.uis.security.service.impl;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import by.kovzov.uis.common.exception.ServiceException;
import by.kovzov.uis.security.dto.JwtAuthenticationDto;
import by.kovzov.uis.security.dto.PermissionDto;
import by.kovzov.uis.security.repository.entity.Permission;
import by.kovzov.uis.security.repository.entity.Role;
import by.kovzov.uis.security.service.model.UserSecurity;
import by.kovzov.uis.security.service.api.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private static final String ISSUER = "uis";
    private static final long ACCESS_TOKEN_EXPIRATION_MINUTES = 10;
    private static final long REFRESH_TOKEN_EXPIRATION_DAYS = 1;
    private static final String ROLE_IDS_CLAIM_NAME = "role_ids";

    private final @Qualifier("jwtAccessTokenEncoder") JwtEncoder jwtAccessTokenEncoder;
    private final @Qualifier("jwtRefreshTokenEncoder") JwtEncoder jwtRefreshTokenEncoder;

    @Override
    public List<Long> extractRoleIds(String accessToken) {
        var auth = new BearerTokenAuthenticationToken(accessToken);
        if (!(auth.getPrincipal() instanceof Jwt jwt)) {
            throw new IllegalArgumentException("Invalid principal type: expected a Jwt token");
        }
        return jwt.getClaimAsStringList(ROLE_IDS_CLAIM_NAME).stream()
            .map(Long::valueOf)
            .toList();
    }

    public JwtAuthenticationDto createTokens(Authentication authentication) {
        if (!(authentication.getPrincipal() instanceof UserSecurity userSecurity)) {
            throw new BadCredentialsException(
                MessageFormat.format("Principal {0} is not of UserSecurity type", authentication.getPrincipal().getClass()));
        }
        return JwtAuthenticationDto.builder()
            .id(userSecurity.getId())
            .accessToken(createAccessToken(userSecurity))
            .refreshToken(updateRefreshToken(authentication))
            .build();
    }

    private String createAccessToken(UserSecurity userSecurity) {
        List<?> authorities = userSecurity.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .toList();
        List<Long> roleIds = userSecurity.getUser().getRoles().stream()
            .map(Role::getId)
            .toList();
        Instant now = Instant.now();
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
            .issuer(ISSUER)
            .issuedAt(now)
            .expiresAt(now.plus(ACCESS_TOKEN_EXPIRATION_MINUTES, ChronoUnit.MINUTES))
            .subject(userSecurity.getUsername())
            .claim("authorities", authorities)
            .claim("user_id", userSecurity.getId())
            .claim(ROLE_IDS_CLAIM_NAME, roleIds)
            .build();
        return jwtAccessTokenEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }

    private String updateRefreshToken(Authentication authentication) {
        String refreshToken = null;
        if (authentication.getCredentials() instanceof Jwt jwt) {
            Instant now = Instant.now();
            Instant expiresAt = jwt.getExpiresAt();
            Duration duration = Duration.between(now, expiresAt);
            long hoursUntilExpired = duration.toHours();
            if (hoursUntilExpired < 1) {
                refreshToken = createRefreshToken(authentication.getName());
            } else {
                refreshToken = jwt.getTokenValue();
            }
        } else {
            refreshToken = createRefreshToken(authentication.getName());
        }

        return refreshToken;
    }

    private String createRefreshToken(String username) {
        Instant now = Instant.now();
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
            .issuer(ISSUER)
            .issuedAt(now)
            .expiresAt(now.plus(REFRESH_TOKEN_EXPIRATION_DAYS, ChronoUnit.DAYS))
            .subject(username)
            .build();
        return jwtRefreshTokenEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }
}
