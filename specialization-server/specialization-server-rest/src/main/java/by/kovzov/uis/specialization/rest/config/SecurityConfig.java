package by.kovzov.uis.specialization.rest.config;

import by.kovzov.uis.specialization.rest.converter.JwtAuthenticationTokenConverter;
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

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   JwtAuthenticationTokenConverter jwtAuthenticationTokenConverter,
                                                   @Value("${jwk-set-uri}") String jwkSetUri) throws Exception {
        http.authorizeHttpRequests()
            .anyRequest()
            .authenticated();
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .oauth2ResourceServer()
            .jwt()
            .jwtAuthenticationConverter(jwtAuthenticationTokenConverter)
            .jwkSetUri(jwkSetUri);
        http.httpBasic().disable();

        return http.build();
    }
}
