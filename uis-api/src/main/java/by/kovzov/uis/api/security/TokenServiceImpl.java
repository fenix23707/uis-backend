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

@Service
public class TokenServiceImpl implements TokenService{

    private final JwtEncoder accessTokenEncoder;

    private final JwtEncoder refreshTokenEncoder;

    public TokenServiceImpl(JwtEncoder accessTokenEncoder, @Qualifier("jwtRefreshTokenEncoder") JwtEncoder refreshTokenEncoder) {
        this.accessTokenEncoder = accessTokenEncoder;
        this.refreshTokenEncoder = refreshTokenEncoder;
    }

    @Override
    public JwtAuthenticationResponse createToken(Authentication authentication) {
        if (!(authentication.getPrincipal() instanceof UserSecurity userSecurity)) {
            throw new BadCredentialsException(
                MessageFormat.format("Principal {0} is not of UserSecurity type", authentication.getPrincipal().getClass()));
        }
        //TODO move to separate method
        String refreshToken;
        if (authentication.getCredentials() instanceof Jwt jwt) {
            Instant now = Instant.now();
            Instant expiresAt = jwt.getExpiresAt();
            Duration duration = Duration.between(now, expiresAt);
            long daysUntilExpired = duration.toDays();
            if (daysUntilExpired < 7) {
                refreshToken = createRefreshToken(authentication);
            } else {
                refreshToken = jwt.getTokenValue();
            }
        } else {
            refreshToken = createRefreshToken(authentication);
        }

        return JwtAuthenticationResponse.builder()
            .id(userSecurity.getId())
            .accessToken(createAccessToken(authentication))
            .refreshToken(refreshToken)
            .build();
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
            .subject(String.valueOf(userSecurity.getId()))
            .build();
        return refreshTokenEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }
}
