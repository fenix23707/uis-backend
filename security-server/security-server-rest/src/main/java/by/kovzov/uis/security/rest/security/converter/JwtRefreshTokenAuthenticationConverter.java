package by.kovzov.uis.security.rest.security.converter;

import by.kovzov.uis.security.rest.security.model.UserSecurity;
import by.kovzov.uis.security.rest.security.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtRefreshTokenAuthenticationConverter implements Converter<Jwt, UsernamePasswordAuthenticationToken> {

    private final UserDetailsServiceImpl userDetailsService;

    @Override
    public UsernamePasswordAuthenticationToken convert(Jwt source) {
        UserSecurity userSecurity = userDetailsService.loadUserByUsername(source.getSubject());
        return new UsernamePasswordAuthenticationToken(userSecurity, source, userSecurity.getAuthorities());
    }
}
