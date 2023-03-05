package by.kovzov.uis.security.rest.config;

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
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import by.kovzov.uis.security.rest.security.exception.AccessDeniedExceptionHandler;
import by.kovzov.uis.security.rest.security.converter.JwtAuthenticationTokenConverter;
import by.kovzov.uis.security.rest.security.converter.JwtRefreshTokenAuthenticationConverter;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] EXPOSED_ENDPOINTS =
        {"/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/api/security/tokens/**"};

    @Bean
    public SecurityFilterChain basicSecurityFilterChain(HttpSecurity http,
                                                        AccessDeniedExceptionHandler accessDeniedExceptionHandler,
                                                        AuthenticationEntryPoint authenticationEntryPoint)
        throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
            .requestMatchers(EXPOSED_ENDPOINTS).permitAll()
            .anyRequest().authenticated()
        );
        http.exceptionHandling((exceptions) -> exceptions
            .authenticationEntryPoint(authenticationEntryPoint)
            .accessDeniedHandler(accessDeniedExceptionHandler)
        );
        http.csrf().disable()
            .cors().and()
            .httpBasic().disable();

        return http.build();
    }

    @Bean
    public SecurityFilterChain oauthSecurityFilterChain(HttpSecurity http,
                                                        @Qualifier("jwtRefreshTokenDecoder") JwtDecoder jwtAccessTokenDecoder,
                                                        JwtAuthenticationTokenConverter converter) throws Exception {
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .oauth2ResourceServer()
            .jwt()
            .jwtAuthenticationConverter(converter)
            .decoder(jwtAccessTokenDecoder);
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
