package by.kovzov.uis.security.rest.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import by.kovzov.uis.security.domain.entity.User;
import by.kovzov.uis.security.service.exception.ServiceException;
import by.kovzov.uis.security.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserServiceImpl userServiceImpl;

    @Override
    public UserSecurity loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userServiceImpl.getByUsername(username);
            return UserSecurity.from(user);
        } catch (ServiceException e) {
            throw new UsernameNotFoundException(e.getReason());
        }
    }
}
