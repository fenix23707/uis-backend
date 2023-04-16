package by.kovzov.uis.security.rest.security.service;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import by.kovzov.uis.security.dto.JwtAuthenticationDto;
import by.kovzov.uis.security.rest.security.model.UserSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

    private static final String ISSUER = "uis";
    private static final long ACCESS_TOKEN_EXPIRATION_MINUTES = 10;
    private static final long REFRESH_TOKEN_EXPIRATION_DAYS = 1;

    private final String userIdClaimName;
    private final JwtEncoder jwtAccessTokenEncoder;
    private final JwtEncoder jwtRefreshTokenEncoder;

    public TokenServiceImpl(@Value("${uis.claim-name.user-id}") String userIdClaimName,
                            @Qualifier("jwtAccessTokenEncoder") JwtEncoder jwtAccessTokenEncoder,
                            @Qualifier("jwtRefreshTokenEncoder") JwtEncoder jwtRefreshTokenEncoder) {
        this.userIdClaimName = userIdClaimName;
        this.jwtAccessTokenEncoder = jwtAccessTokenEncoder;
        this.jwtRefreshTokenEncoder = jwtRefreshTokenEncoder;
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
        Instant now = Instant.now();
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
            .issuer(ISSUER)
            .issuedAt(now)
            .expiresAt(now.plus(ACCESS_TOKEN_EXPIRATION_MINUTES, ChronoUnit.MINUTES))
            .subject(userSecurity.getUsername())
            .claim("authorities", authorities)
            .claim(userIdClaimName, userSecurity.getId())
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
