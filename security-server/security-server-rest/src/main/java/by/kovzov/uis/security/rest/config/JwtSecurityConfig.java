package by.kovzov.uis.security.rest.config;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import by.kovzov.uis.security.rest.security.service.SecurityKeysService;
import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class JwtSecurityConfig {

    private final SecurityKeysService securityKeysService;

    @Bean
    public JwtEncoder jwtAccessTokenEncoder() {
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(accessTokenRsaKey()));
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    public JwtDecoder jwtAccessTokenDecoder() throws JOSEException {
        return NimbusJwtDecoder.withPublicKey(accessTokenRsaKey().toRSAPublicKey()).build();
    }

    @Bean
    public RSAKey accessTokenRsaKey() {
        return securityKeysService.createNewRsaKey();
    }

    @Bean
    public JwtEncoder jwtRefreshTokenEncoder() {
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(refreshTokenRsaKey()));
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    public JwtDecoder jwtRefreshTokenDecoder() throws JOSEException {
        return NimbusJwtDecoder.withPublicKey(refreshTokenRsaKey().toRSAPublicKey()).build();
    }

    @Bean
    public RSAKey refreshTokenRsaKey() {
        return securityKeysService.createNewRsaKey();
    }
}