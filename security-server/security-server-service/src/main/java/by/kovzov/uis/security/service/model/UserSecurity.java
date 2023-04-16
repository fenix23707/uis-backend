package by.kovzov.uis.security.service.model;

import java.util.Collection;
import java.util.stream.Collectors;

import by.kovzov.uis.security.repository.entity.Role;
import by.kovzov.uis.security.repository.entity.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@EqualsAndHashCode
public class UserSecurity implements UserDetails {

    @Getter
    private final Long id;

    @Getter
    private final User user;

    public UserSecurity(User user) {
        this.user = user;
        this.id = user.getId();
    }

    private static Collection<GrantedAuthority> mapToAuthorities(Collection<Role> roles) {
        return roles.stream()
            .map(Role::getPermissions)
            .flatMap(Collection::stream)
            .distinct()
            .map(permission -> "%s_%s".formatted(permission.getScope(), permission.getAction()))
            .map(String::toUpperCase)
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return mapToAuthorities(user.getRoles());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
