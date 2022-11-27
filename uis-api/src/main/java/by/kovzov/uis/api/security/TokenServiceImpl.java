package by.kovzov.uis.api.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import by.kovzov.uis.domain.dto.response.JwtAuthenticationResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService{

    private final JwtEncoder accessTokenEncoder;
    @Qualifier("jwtRefreshTokenEncoder")
    private final JwtEncoder refreshTokenEncoder;

    @Override
    public JwtAuthenticationResponse createToken(Authentication authentication) {
        if (!(authentication.getPrincipal() instanceof UserSecurity userSecurity)) {
            throw new BadCredentialsException(
                MessageFormat.format("Principal {0} is not of UserSecurity type", authentication.getPrincipal().getClass()));
        }
        return JwtAuthenticationResponse.builder()
            .id(userSecurity.getId())
            .accessToken(createAccessToken(authentication))
            .refreshToken(updateRefreshToken(authentication))
            .build();
    }

    private String updateRefreshToken(Authentication authentication) {
        String refreshToken;
        if (authentication.getCredentials() instanceof Jwt jwt) {
            Instant now = Instant.now();
            Instant expiresAt = jwt.getExpiresAt();
            Duration duration = Duration.between(now, expiresAt);
            long hoursUntilExpired = duration.toHours();
            if (hoursUntilExpired < 2) {
                refreshToken = createRefreshToken(authentication);
            } else {
                refreshToken = jwt.getTokenValue();
            }
        } else {
            refreshToken = createRefreshToken(authentication);
        }

        return refreshToken;
    }

    private String createAccessToken(Authentication authentication) {
        UserSecurity userSecurity = (UserSecurity) authentication.getPrincipal();
        Instant now = Instant.now();
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
            .issuer("uis")
            .issuedAt(now)
            .expiresAt(now.plus(10, ChronoUnit.MINUTES))
            .subject(userSecurity.getUsername())
            .build();
        return accessTokenEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }

    private String createRefreshToken(Authentication authentication) {
        UserSecurity userSecurity = (UserSecurity) authentication.getPrincipal();
        Instant now = Instant.now();
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
            .issuer("uis")
            .issuedAt(now)
            .expiresAt(now.plus(1, ChronoUnit.DAYS))
            .subject(userSecurity.getUsername())
            .build();
        return refreshTokenEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }
}
