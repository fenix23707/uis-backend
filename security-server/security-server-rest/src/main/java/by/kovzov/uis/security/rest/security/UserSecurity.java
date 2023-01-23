package by.kovzov.uis.security.rest.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.stream.Collectors;

import by.kovzov.uis.security.domain.entity.User;
import by.kovzov.uis.security.domain.entity.UserRole;
import by.kovzov.uis.security.domain.entity.UserStatus;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@EqualsAndHashCode
public class UserSecurity implements UserDetails {

    @Getter
    private final Long id;
    private final String username;
    private final String password;
    private final Collection<GrantedAuthority> authorities;
    private final boolean isActive;

    public static UserSecurity from(User user) {
        return UserSecurity.builder()
            .id(user.getId())
            .username(user.getUsername())
            .password(user.getPassword())
            .authorities(mapToauthorities(user.getRoles()))
            .isActive(user.getStatus().equals(UserStatus.ACTIVE))
            .build();
    }

    private static Collection<GrantedAuthority> mapToauthorities(Collection<UserRole> roles) {
        return roles.stream()
            .map(UserRole::getPermissions)
            .flatMap(Collection::stream)
            .distinct()
            .map(permission -> MessageFormat.format("{0}_{1}", permission.getScope(), permission.getAction()))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}