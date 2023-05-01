package by.kovzov.uis.academic.rest.config;

import by.kovzov.uis.academic.rest.converter.JwtAuthenticationTokenConverter;
import by.kovzov.uis.common.exception.handler.security.AccessDeniedExceptionHandler;
import by.kovzov.uis.common.exception.handler.security.AuthenticationExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private static final String[] EXPOSED_ENDPOINTS = {"/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   JwtAuthenticationTokenConverter jwtAuthenticationTokenConverter,
                                                   @Value("${jwk-set-uri}") String jwkSetUri,
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
            .jwtAuthenticationConverter(jwtAuthenticationTokenConverter)
            .jwkSetUri(jwkSetUri);
        http.exceptionHandling((exceptions) -> exceptions
            .authenticationEntryPoint(authenticationExceptionHandler)
            .accessDeniedHandler(accessDeniedExceptionHandler)
        );
        http.httpBasic().disable();

        return http.build();
    }
}
