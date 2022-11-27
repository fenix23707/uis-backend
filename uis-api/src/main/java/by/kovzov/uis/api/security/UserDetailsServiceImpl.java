package by.kovzov.uis.api.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import by.kovzov.uis.domain.entity.User;
import by.kovzov.uis.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserServiceImpl userServiceImpl;

    @Override
    public UserSecurity loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userServiceImpl.getByUsername(username);
        return UserSecurity.from(user);
    }
}
