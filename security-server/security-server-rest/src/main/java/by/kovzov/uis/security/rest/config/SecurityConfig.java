package by.kovzov.uis.security.rest.config;

import by.kovzov.uis.common.exception.handler.security.AccessDeniedExceptionHandler;
import by.kovzov.uis.common.exception.handler.security.AuthenticationExceptionHandler;
import by.kovzov.uis.security.rest.converter.JwtAuthenticationTokenConverter;
import by.kovzov.uis.security.rest.converter.JwtRefreshTokenAuthenticationConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] EXPOSED_ENDPOINTS = {
        "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html",
        "/api/security/tokens/**", "/api/security/jwk-set-uri",
        "/api/authorization/**"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   @Qualifier("jwtAccessTokenDecoder") JwtDecoder jwtAccessTokenDecoder,
                                                   JwtAuthenticationTokenConverter converter,
                                                   AccessDeniedExceptionHandler accessDeniedExceptionHandler,
                                                   AuthenticationExceptionHandler authenticationExceptionHandler) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
            .requestMatchers(EXPOSED_ENDPOINTS).permitAll()
            .anyRequest().authenticated()
        );
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .oauth2ResourceServer()
            .authenticationEntryPoint(authenticationExceptionHandler)
            .accessDeniedHandler(accessDeniedExceptionHandler)
            .jwt()
            .jwtAuthenticationConverter(converter)
            .decoder(jwtAccessTokenDecoder);
        http.exceptionHandling((exceptions) -> exceptions
            .authenticationEntryPoint(authenticationExceptionHandler)
            .accessDeniedHandler(accessDeniedExceptionHandler)
        );
        http.csrf().disable()
            .cors().and()
            .httpBasic().disable();

        return http.build();
    }

    @Bean
    public JwtAuthenticationProvider jwtRefreshTokenAuthProvider(@Qualifier("jwtRefreshTokenDecoder") JwtDecoder jwtRefreshTokenDecoder,
                                                                 JwtRefreshTokenAuthenticationConverter jwtRefreshTokenAuthenticationConverter) {
        JwtAuthenticationProvider provider = new JwtAuthenticationProvider(jwtRefreshTokenDecoder);
        provider.setJwtAuthenticationConverter(jwtRefreshTokenAuthenticationConverter);
        return provider;
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
