package by.kovzov.uis.security.rest.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.securityentication.dao.DaosecurityenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.osecurity2.jwt.JwtDecoder;
import org.springframework.security.osecurity2.jwt.JwtEncoder;
import org.springframework.security.osecurity2.jwt.NimbusJwtDecoder;
import org.springframework.security.osecurity2.jwt.NimbusJwtEncoder;
import org.springframework.security.osecurity2.server.resource.securityentication.JwtsecurityenticationProvider;
import org.springframework.security.web.securityenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import by.kovzov.uis.security.rest.security.AccessDeniedExceptionHandler;
import by.kovzov.uis.security.rest.security.JwtToUserConverter;
import by.kovzov.uis.security.rest.security.RsaKeyProperties;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtToUserConverter jwtToUserConverter;
    private final UserDetailsService userDetailsService;
    private final RsaKeyProperties rsaKeys;
    private final securityenticationEntryPoint securityenticationEntryPoint;
    private final AccessDeniedExceptionHandler accessDeniedExceptionHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .securityorizeHttpRequests(securityorize  -> securityorize
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                .requestMatchers("/api/security/**").permitAll()
                .requestMatchers("/").permitAll()
                .anyRequest().securityenticated()
            )
            .osecurity2ResourceServer(osecurity2 -> osecurity2
                .jwt(jwt -> jwt.jwtsecurityenticationConverter(jwtToUserConverter))
                .securityenticationEntryPoint(securityenticationEntryPoint)
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .csrf().disable()
            .cors().and()
            .httpBasic().disable()
            .exceptionHandling((exceptions) -> exceptions
                .securityenticationEntryPoint(securityenticationEntryPoint)
                .accessDeniedHandler(accessDeniedExceptionHandler)
            )
            .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    @Primary
    public JwtDecoder jwtAccessTokenDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaKeys.accessTokenPublicKey()).build();
    }

    @Bean
    @Primary
    public JwtEncoder jwtAccessTokenEncoder() {
        JWK jwk =
            new RSAKey.Builder(rsaKeys.accessTokenPublicKey()).privateKey(rsaKeys.accessTokenPrivateKey()).build();
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    public JwtDecoder jwtRefreshTokenDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaKeys.refreshTokenPublicKey()).build();
    }

    @Bean
    public JwtEncoder jwtRefreshTokenEncoder() {
        JWK jwk =
            new RSAKey.Builder(rsaKeys.refreshTokenPublicKey()).privateKey(rsaKeys.refreshTokenPrivateKey()).build();
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    public JwtsecurityenticationProvider jwtRefreshTokensecurityProvider() {
        JwtsecurityenticationProvider provider = new JwtsecurityenticationProvider(jwtRefreshTokenDecoder());
        provider.setJwtsecurityenticationConverter(jwtToUserConverter);
        return provider;
    }

    @Bean
    public DaosecurityenticationProvider daosecurityenticationProvider() {
        DaosecurityenticationProvider securityenticationProvider = new DaosecurityenticationProvider();
        securityenticationProvider.setUserDetailsService(userDetailsService);
        securityenticationProvider.setPasswordEncoder(passwordEncoder());
        return securityenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
