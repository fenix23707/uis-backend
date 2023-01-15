package by.kovzov.uis.security.rest.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtToUserConverter implements Converter<Jwt, UsernamePasswordAuthenticationToken> {

    private final UserDetailsServiceImpl userDetailsService;

    @Override
    public UsernamePasswordAuthenticationToken convert(Jwt jwt) {
        UserSecurity userSecurity = userDetailsService.loadUserByUsername(jwt.getSubject());
        return new UsernamePasswordAuthenticationToken(userSecurity, jwt, userSecurity.getAuthorities());
    }
}
