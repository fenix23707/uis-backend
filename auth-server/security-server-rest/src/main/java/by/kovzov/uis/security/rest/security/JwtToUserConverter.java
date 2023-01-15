package by.kovzov.uis.security.rest.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.securityentication.UsernamePasswordsecurityenticationToken;
import org.springframework.security.osecurity2.jwt.Jwt;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtToUserConverter implements Converter<Jwt, UsernamePasswordsecurityenticationToken> {

    private final UserDetailsServiceImpl userDetailsService;

    @Override
    public UsernamePasswordsecurityenticationToken convert(Jwt jwt) {
        UserSecurity userSecurity = userDetailsService.loadUserByUsername(jwt.getSubject());
        return new UsernamePasswordsecurityenticationToken(userSecurity, jwt, userSecurity.getsecurityorities());
    }
}
