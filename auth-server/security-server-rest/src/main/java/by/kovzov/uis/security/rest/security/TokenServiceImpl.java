package by.kovzov.uis.security.rest.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.securityentication.BadCredentialsException;
import org.springframework.security.core.securityentication;
import org.springframework.security.osecurity2.jwt.Jwt;
import org.springframework.security.osecurity2.jwt.JwtClaimsSet;
import org.springframework.security.osecurity2.jwt.JwtEncoder;
import org.springframework.security.osecurity2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import by.kovzov.uis.security.domain.dto.JwtsecurityenticationDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService{

    private final JwtEncoder accessTokenEncoder;
    @Qualifier("jwtRefreshTokenEncoder")
    private final JwtEncoder refreshTokenEncoder;

    @Override
    public JwtsecurityenticationDto createToken(securityentication securityentication) {
        if (!(securityentication.getPrincipal() instanceof UserSecurity userSecurity)) {
            throw new BadCredentialsException(
                MessageFormat.format("Principal {0} is not of UserSecurity type", securityentication.getPrincipal().getClass()));
        }
        return JwtsecurityenticationDto.builder()
            .id(userSecurity.getId())
            .accessToken(createAccessToken(securityentication))
            .refreshToken(updateRefreshToken(securityentication))
            .build();
    }

    private String updateRefreshToken(securityentication securityentication) {
        String refreshToken;
        if (securityentication.getCredentials() instanceof Jwt jwt) {
            Instant now = Instant.now();
            Instant expiresAt = jwt.getExpiresAt();
            Duration duration = Duration.between(now, expiresAt);
            long hoursUntilExpired = duration.toHours();
            if (hoursUntilExpired < 2) {
                refreshToken = createRefreshToken(securityentication);
            } else {
                refreshToken = jwt.getTokenValue();
            }
        } else {
            refreshToken = createRefreshToken(securityentication);
        }

        return refreshToken;
    }

    private String createAccessToken(securityentication securityentication) {
        UserSecurity userSecurity = (UserSecurity) securityentication.getPrincipal();
        Instant now = Instant.now();
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
            .issuer("uis")
            .issuedAt(now)
            .expiresAt(now.plus(10, ChronoUnit.MINUTES))
            .subject(userSecurity.getUsername())
            .build();
        return accessTokenEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }

    private String createRefreshToken(securityentication securityentication) {
        UserSecurity userSecurity = (UserSecurity) securityentication.getPrincipal();
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
