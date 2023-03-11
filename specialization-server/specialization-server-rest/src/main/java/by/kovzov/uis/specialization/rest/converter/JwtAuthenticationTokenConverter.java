package by.kovzov.uis.specialization.rest.converter;

import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationTokenConverter implements Converter<Jwt, JwtAuthenticationToken> {

    @Override
    @SuppressWarnings("unchecked")
    public JwtAuthenticationToken convert(Jwt source) {
        List<String> stringAuthorities = (List<String>) source.getClaims().get("authorities");
        List<? extends GrantedAuthority> authorities = stringAuthorities.stream()
            .map(SimpleGrantedAuthority::new)
            .toList();
        return new JwtAuthenticationToken(source, authorities);
    }
}
