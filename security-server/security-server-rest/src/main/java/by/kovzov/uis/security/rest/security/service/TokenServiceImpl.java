package by.kovzov.uis.security.rest.security.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import by.kovzov.uis.security.domain.dto.JwtAuthenticationDto;
import by.kovzov.uis.security.rest.security.model.UserSecurity;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final @Qualifier("jwtAccessTokenEncoder") JwtEncoder jwtAccessTokenEncoder;
    private final @Qualifier("jwtRefreshTokenEncoder") JwtEncoder jwtRefreshTokenEncoder;

    public JwtAuthenticationDto createTokens(Authentication authentication) {
        if (!(authentication.getPrincipal() instanceof UserSecurity userSecurity)) {
            throw new BadCredentialsException(
                MessageFormat.format("Principal {0} is not of UserSecurity type", authentication.getPrincipal().getClass()));
        }
        return JwtAuthenticationDto.builder()
            .id(userSecurity.getId())
            .accessToken(createAccessToken(userSecurity))
            .refreshToken(createRefreshToken(userSecurity.getUsername()))
            .build();
    }

    private String updateRefreshToken(Authentication authentication) {
        String refreshToken;
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

    private String createAccessToken(UserSecurity userSecurity) {
        List<?> authorities = userSecurity.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .toList();
        Instant now = Instant.now();
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
            .issuer("uis")
            .issuedAt(now)
            .expiresAt(now.plus(10, ChronoUnit.MINUTES))
            .subject(userSecurity.getUsername())
            .claim("authorities", authorities)
            .build();
        return jwtAccessTokenEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }

    private String createRefreshToken(String username) {
        Instant now = Instant.now();
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
            .issuer("uis")
            .issuedAt(now)
            .expiresAt(now.plus(1, ChronoUnit.DAYS))
            .subject(username)
            .build();
        return jwtRefreshTokenEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }
}
