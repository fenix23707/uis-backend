package by.kovzov.uis.security.service.impl;

import by.kovzov.uis.security.repository.entity.User;
import by.kovzov.uis.security.service.model.UserSecurity;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserServiceImpl userServiceImpl;

    @Override
    public UserSecurity loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userServiceImpl.getByUsername(username);
            return new UserSecurity(user);
        } catch (ServiceException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}
